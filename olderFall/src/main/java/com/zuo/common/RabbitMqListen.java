package com.zuo.common;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zuo.entity.*;
import com.zuo.service.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author 橙宝cc
 * @date 2022/12/7 - 13:06
 */
@Component
public class RabbitMqListen {

    @Autowired
    private FamilyDataService familyDataService;//家庭雷达数据

    @Autowired
    private RadarDataService radarDataService;//雷达数据

    @Autowired
    private OldPhService oldPhService;//老人身体数据

    @Autowired
    private RadarStatusService radarStatusService;//雷达状态

    @Autowired
    private WatchStatusService watchStatusService;//手表状态

    @RabbitListener(queues = "work-queue")
    @RabbitHandler
    public void workQueue(String str) {
        System.out.println("当前监听到了：" + str);
        JSONObject jsonObject = JSONObject.parseObject(str);//转换为json对象
        String netty_code = jsonObject.getString("netty_code");//获取netty_code号
        jsonObject = jsonObject.getJSONObject("msg");
        Integer type = jsonObject.getInteger("type");
        Integer update = jsonObject.getInteger("update");
        if(type == null)type = 0;
        Integer status = jsonObject.getInteger("status");
        if(status != null)
        {
            if(type == 1)
            {
                System.out.println("雷达状态信息");
                RadarStatus radarStatus = new RadarStatus();
                Integer id = jsonObject.getInteger("id");
                if(id!=null){
                radarStatus.setId(id);
                radarStatus.setStatus(status);
                radarStatus.setNettyCode(netty_code);
                radarStatusService.updateById(radarStatus);}
            }else if(type == 2){
                System.out.println("手表状态信息");
                WatchStatus watchStatus = new WatchStatus();
                Integer id = jsonObject.getInteger("id");
                watchStatus.setId(id);
                watchStatus.setStatus(status);
                watchStatus.setNettyCode(netty_code);
                watchStatusService.updateById(watchStatus);
            }
            else{
                QueryWrapper<RadarStatus> queryWrapper = new QueryWrapper<>();
                QueryWrapper<WatchStatus> queryWrapper1 = new QueryWrapper<>();
                queryWrapper.eq("netty_code",netty_code);
                queryWrapper1.eq("netty_code",netty_code);
                RadarStatus radarStatus1 = radarStatusService.getOne(queryWrapper);
                WatchStatus watchStatus = watchStatusService.getOne(queryWrapper1);
                if(radarStatus1 != null)
                {
                    radarStatus1.setStatus(0);
                    radarStatus1.setNettyCode("暂无链接");
                    radarStatusService.updateById(radarStatus1);
                }
                else if(watchStatus!=null)
                {
                    watchStatus.setStatus(0);
                    watchStatus.setNettyCode("暂无链接");
                    watchStatusService.updateById(watchStatus);
                }else {
                    System.out.println("未查询到连接信息");
                }

            }
        }
        else if(type != null){
            if(update != null){
                JSONObject r_data = jsonObject.getJSONObject("r_data");
                //获取距离和角度(循环访问存储) 这里接收数据需要完善
                ArrayList<FamilyData> familyList = new ArrayList<>();

                for(int i = 0;i <= 360;i++) {
                    Double dis = r_data.getDouble("dis" + i);
                    String angle = r_data.getString("angle" + i);
                    Integer id = jsonObject.getInteger("id");
                    //存储数据
                    FamilyData familyData = new FamilyData();
                    familyData.setRDist(dis);
                    familyData.setId(id);
                    familyData.setAngle(String.valueOf(angle));
                    familyList.add(familyData);
                }
                familyDataService.updateFamily(familyList);
            }else{
                if (type == 1) {
                    JSONObject r_data = jsonObject.getJSONObject("r_data");
                    System.out.println("雷达数据");
                    //获取距离和角度(循环访问存储) 这里接受数据需要完善
                    ArrayList<FamilyData> familyList = new ArrayList<>();

                    for(int i = 0;i <= 68;i++) {
                        Double dis = r_data.getDouble("dist" + i);
                        String angle = r_data.getString("angle" + i);
                        Integer id = jsonObject.getInteger("id");
                        //存储数据
                        RadarData radarData = new RadarData();
                        radarData.setId(id);
                        radarData.setRDist(dis);
                        radarData.setAngle(angle);
                        radarData.setTime(LocalDateTime.now());
                        radarDataService.save(radarData);
                    }

                }
                else {
                    System.out.println("手表数据");
                    OldPh oldPh = new OldPh();
                    Integer id = jsonObject.getInteger("id");
                    String heart = jsonObject.getString("heart");
                    String blood = jsonObject.getString("blood");
                    String template = jsonObject.getString("template");
                    Integer fall = jsonObject.getInteger("fall");
                    oldPh.setId(id);
                    oldPh.setHeartRate(heart);
                    oldPh.setBloodPressure(blood);
                    oldPh.setTemperature(template);
                    oldPh.setFall(fall);
                    oldPh.setTime(LocalDateTime.now());
                    oldPhService.save(oldPh);
                }
            }
        }
        else {
            System.out.println("接收到了其他数据");
        }
    }
}
// {"id":1,"type":1,"dis":100,"angle":50}
// {"id":1,"type":1,"update":1,r_data:{"dis":100,"angle":50}}  家庭环境构建
// {"id":1,"type":1,r_data:}
// {"id":1,"type":2,"heart":100,"blood":50,"template":36,"fall":0}
// {"id":1,"type":1,"status":1}
// {"content":"hello"}
