package com.zuo.common;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zuo.entity.OldPh;
import com.zuo.entity.RadarData;
import com.zuo.entity.RadarStatus;
import com.zuo.entity.WatchStatus;
import com.zuo.service.OldPhService;
import com.zuo.service.RadarDataService;
import com.zuo.service.RadarStatusService;
import com.zuo.service.WatchStatusService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author 橙宝cc
 * @date 2022/12/7 - 13:06
 */
@Component
public class RabbitMqListen {

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
        JSONObject jsonObject = JSONObject.parseObject(str);
        String netty_code = jsonObject.getString("netty_code");
        jsonObject = jsonObject.getJSONObject("msg");
        Integer type = jsonObject.getInteger("type");
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
            if (type == 1) {
                System.out.println("雷达数据");
                Integer id = jsonObject.getInteger("id");
                Double dis = jsonObject.getDouble("dis");
                String angle = jsonObject.getString("angle");
                RadarData radarData = new RadarData();
                radarData.setId(id);
                radarData.setRDist(dis);
                radarData.setAngle(angle);
                radarData.setTime(LocalDateTime.now());
                radarDataService.save(radarData);
            } else {
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
        else {
            System.out.println("接收到了其他数据");
        }
    }
}
// {"id":1,"type":1,"dis":100,"angle":50}
// {"id":1,"type":2,"heart":100,"blood":50,"template":36,"fall":0}
// {"id":1,"type":1,"status":1}
// {"content":"hello"}
