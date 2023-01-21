package com.zuo.netty;

import com.alibaba.fastjson.JSONObject;

import com.zuo.common.R;
import com.zuo.entity.DoorData;
import com.zuo.entity.FamilyData;
import com.zuo.entity.PeopleNum;
import com.zuo.service.CloudDataService;
import com.zuo.service.DoorDataService;
import com.zuo.service.FamilyDataService;
import com.zuo.service.PeopleNumService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaocg
 */
@Component
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private static RabbitTemplate rabbitTemplate;

    private static FamilyDataService familyDataService;

    private static DoorDataService doorDataService;

    private static PeopleNumService peopleNumService;

    private static CloudDataService cloudDataService;
    private static Map<String, Channel> map = new ConcurrentHashMap<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate){
        WebSocketHandler.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setFamilyDataService(FamilyDataService familyDataService){
        WebSocketHandler.familyDataService = familyDataService;
    }
    @Autowired
    public void setCloudDataService(CloudDataService cloudDataService){
        WebSocketHandler.cloudDataService = cloudDataService;
    }
    @Autowired
    public void setDoorDataService(PeopleNumService peopleNumService){
        WebSocketHandler.peopleNumService = peopleNumService;
    }

    @Autowired
    public void setPeopleNumService(DoorDataService doorDataService){
        WebSocketHandler.doorDataService = doorDataService;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object object) throws Exception {
        String msg = "";
        if (object instanceof TextWebSocketFrame){
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame)object;
            msg = textWebSocketFrame.text();
        }else {
            msg = String.valueOf(object);
            System.out.println("发送信息的管道"+ctx.channel().id().asLongText());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg",msg);
            jsonObject.put("netty_code",ctx.channel().id().asLongText());
            //交给rabbitmq进行存储
            rabbitTemplate.convertAndSend("work-queue",jsonObject.toJSONString());
            //进行数据判断

            JSONObject data_obj = JSONObject.parseObject(jsonObject.toJSONString());
            data_obj = data_obj.getJSONObject("msg");
            Integer update = data_obj.getInteger("update");
            Integer id = data_obj.getInteger("id");
            JSONObject r_data = data_obj.getJSONObject("r_data");
            if(update == null && r_data!=null)
            {
                //这里需要完善
                //首先获取到数据库中所有的数据
                List<FamilyData> familyDataById = familyDataService.getFamilyDataById(id);
                //对比
                List<FamilyData> familyData = new ArrayList<>();//存储对比之后的数据
                for(int i = 0;i <= 81;i++)//获取r_data发送过来的数据
                {
                    Double dis = r_data.getDouble("dist" + i);
                    String angle = r_data.getString("angle" + i);
                    if(dis == null && angle == null)break;
                    boolean flag = false;
                    for(FamilyData t : familyDataById)//获取数据库中的数据
                    {
                        //门口数据没保存，所以会被分开
                        if(t.getAngle().equals(angle)) flag = true;
                        if(flag && Math.abs(dis - t.getRDist()) >= 1)//对比之后距离变化说明不是同一个点
                        {
                            FamilyData familyData1 = new FamilyData();
                            familyData1.setId(id);
                            familyData1.setAngle(angle);
                            familyData1.setRDist(dis);
                            familyData.add(familyData1);//存储发现不同的数据
                            break;
                        }
                    }
                    if(!flag)
                    {
                        FamilyData familyData1 = new FamilyData();
                        familyData1.setId(id);
                        familyData1.setAngle(angle);
                        familyData1.setRDist(dis);
                        familyData.add(familyData1);//存储发现不同的数据
                    }
                }
                //个体归类
                //先排序，将检测到的所有点按角度进行升序排序
                quick_sort(familyData,0,familyData.size() - 1);
                //每个点逐个扫描，进行族类划分
                List<List<FamilyData>> family_class = new ArrayList<>();
                List<FamilyData> t = new ArrayList<>();
                for(int i = 0, j = i + 1;i < familyData.size();)
                {
                    t.add(familyData.get(i));
                    if(i != familyData.size() - 1) {
                        //获取点之间的信息
                        String angle_s_j = familyData.get(j).getAngle();
                        Double angle_d_j = Double.valueOf(angle_s_j);
                        Double rDist_j = familyData.get(j).getRDist();
                        String angle_s_i = familyData.get(i).getAngle();
                        Double angle_d_i = Double.valueOf(angle_s_i);
                        Double rDist_i = familyData.get(i).getRDist();
                        //判断是否为同一族类
                        if (Math.abs(angle_d_j - angle_d_i) < 2 && Math.abs(rDist_j - rDist_i) < 3) {
                            i = j;
                            j++;
                        } else {
                            family_class.add(t);//添加该族类
                            t = new ArrayList<>();//清空上一个族类
                            i = j;
                            j++;
                        }
                    }else{
                        family_class.add(t);
                        break;
                    }
                }
                System.out.println(family_class.size());
                //添加点云数据
                cloudDataService.updateCloudData(family_class);

                //根据门附近的点，判断是否有人进入
                DoorData doorById = doorDataService.getDoorById(id);
                String x1 = doorById.getX1();
                String y1 = doorById.getY1();
                String x2 = doorById.getX2();
                String y2 = doorById.getY2();
                Double x_m = (Double.valueOf(x1) + Double.valueOf(x2))/2;
                Double y_m = (Double.valueOf(y1) + Double.valueOf(y2))/2;
                Double r = Double.valueOf(doorById.getR());

                int indoorNum = 0,outdoorNum = 0,cloud_num = family_class.size();//增加和减少人数以及点云数量
                for(List<FamilyData> fa: family_class)
                {
                    System.out.println(fa.size());
                    Double x1_f = fa.get(0).getRDist() * Math.sin(Double.valueOf(fa.get(0).getAngle())* (Math.PI / 180));
                    Double y1_f = fa.get(0).getRDist() * Math.cos(Double.valueOf(fa.get(0).getAngle())* (Math.PI / 180));
                    Double y2_f = fa.get(fa.size() - 1).getRDist() * Math.cos(Double.valueOf(fa.get(fa.size() - 1).getAngle())* (Math.PI / 180));
                    Double x2_f = fa.get(fa.size() - 1).getRDist() * Math.sin(Double.valueOf(fa.get(fa.size() - 1).getAngle())* (Math.PI / 180));
                    Double x1_f_m = (x1_f + x2_f)/2;//人体中心点坐标
                    Double y1_f_m = (y1_f + y2_f)/2;//人体中心点坐标
                    //计算与门之间的距离,判断是否需要增加人数
                    Double r_m = Math.sqrt(Math.pow((x1_f_m-x_m),2) + Math.pow((y1_f_m-y_m),2));
                    Double y1_f_m_k = Double.valueOf(doorById.getK()) * x1_f_m + Double.valueOf(doorById.getB());
                    if(r_m <= r/2 && y1_f_m < y1_f_m_k || y1_f_m > y1_f_m_k)
                    {
                        //减少人数
                        outdoorNum++;
                    }else if(r_m > r/2 && r_m <= 4 * r/5 && y1_f_m < y1_f_m_k)
                    {
                        //增加人数
                        indoorNum++;
                    }
                }
                boolean fall = false;
                //判断是否跌倒
                PeopleNum byId = peopleNumService.getById(id);
                Integer peopleNum1 = byId.getPeopleNum();//数据库存储的屋内人数
                if(byId == null)//如果是第一次存储数据,直接存储检测到的屋内人数
                {
                    PeopleNum peopleNum = new PeopleNum();
                    peopleNum.setId(id);
                    peopleNum.setPeopleNum(cloud_num - outdoorNum);
                    peopleNumService.save(peopleNum);
                }else{
                    if(peopleNum1 != cloud_num - outdoorNum)//如果屋内应该存在的人数等于检测到的人数，说明正常
                    {
                         if(peopleNum1 < cloud_num - outdoorNum)//要么故障，要么有人进入
                        {
                            if(peopleNum1 + indoorNum == cloud_num - outdoorNum)//说明有人进入
                            {
                                PeopleNum peopleNum = new PeopleNum();
                                peopleNum.setId(id);
                                peopleNum.setPeopleNum(cloud_num - outdoorNum);
                                peopleNumService.updateById(peopleNum);
                            }else {//暂停使用,等待重置

                            }
                        }else if(peopleNum1 > cloud_num - outdoorNum)//要么有人出门，要么有人跌倒
                        {
                            if(peopleNum1 == cloud_num)//但是如果相等，说明有人出去了
                            {
                                PeopleNum peopleNum = new PeopleNum();
                                peopleNum.setId(id);
                                peopleNum.setPeopleNum(cloud_num - outdoorNum);
                                peopleNumService.updateById(peopleNum);
                            }else {//说明发生跌倒，立刻报警,并等待重置
                                fall = true;
                            }
                        }
                    }
                }

                if(fall)
                    msg = "检测到跌倒";
                else msg = "未检测到跌倒";
            }
        }
        System.out.println("msg:"+msg);

        for (String key: map.keySet()) {
            if (key.equals(ctx.channel().id().toString())){
                continue;
            }
            Channel channel = map.get(key);
            ChannelFuture channelFuture = channel.writeAndFlush(msg);
            if (!channelFuture.isSuccess()) {
                channel.writeAndFlush(new TextWebSocketFrame(msg));
            }
        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded::"+ctx.channel().id().asLongText());
        InetSocketAddress socketAddress = (InetSocketAddress)ctx.channel().remoteAddress();
        String hostAddress = socketAddress.getAddress().getHostAddress();
        logger.info("IP:{}",hostAddress);
        String clientId = ctx.channel().id().toString();
        map.put(clientId,ctx.channel());
        logger.info("map:{}",map.toString());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved::"+ctx.channel().id().asLongText());
        String clientId = ctx.channel().id().toString();
        String msg = "{" + "status:0" + "}";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",msg);
        jsonObject.put("netty_code",ctx.channel().id().asLongText());
        rabbitTemplate.convertAndSend("work-queue",jsonObject.toJSONString());
        map.remove(clientId);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught::"+cause.getMessage());
        String clientId = ctx.channel().id().toString();
        map.remove(clientId);
        ctx.close();
    }

    public static void quick_sort(List<FamilyData> familyDataList,int l,int r)
    {
        if(l >= r)return ;

        int i = l -1 ,j = r + 1;
        FamilyData x = familyDataList.get(l + r >> 1);
        while(i < j)
        {
            while(Double.valueOf(familyDataList.get(++i).getAngle()) < Double.valueOf(x.getAngle()));
            while(Double.valueOf(familyDataList.get(--j).getAngle()) > Double.valueOf(x.getAngle()));
            if(i < j) Collections.swap(familyDataList,i,j);
        }
        quick_sort(familyDataList,l,j);
        quick_sort(familyDataList,j + 1,r);
    }
}
