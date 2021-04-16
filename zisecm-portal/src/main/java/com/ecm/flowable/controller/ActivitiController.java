//package com.ecm.flowable.controller;
//
//import com.ecm.flowable.rocketmq.RocketMQProducer;
//import com.ecm.flowable.service.ActivitiService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: ZhangRui
// * @Description:
// * @date: Created in 9:56 2018/4/3
// * @Modified By:
// */
//@RestController
//@RequestMapping("/")
//@Api(value = "工作流")
//public class ActivitiController {
//
//    @Autowired
//    private ActivitiService activitiService;
//
//    @ApiOperation("查询任务")
//    @RequestMapping(value = "/searchProcess",method = RequestMethod.GET)
//    public List<Map<String,Object>> searchProcess() {
//        return activitiService.searchProcess();
//    }
//
//    @ApiOperation("提交申请")
//    @RequestMapping(value = "/commitProcess",method = RequestMethod.GET)
//    public boolean commitProcess(@RequestParam String duration) {
//
//        activitiService.commitProcess(duration);
//        return true;
//    }
//    @ApiOperation("审核")
//    @RequestMapping(value = "/approveProcess",method = RequestMethod.GET)
//    public boolean approveProcess(@RequestParam boolean approved) {
//
//        activitiService.approveProcess(approved);
//        return true;
//    }
//    @ApiOperation("归还")
//    @RequestMapping(value = "/returnProcess",method = RequestMethod.GET)
//    public boolean returnProcess() {
//
//        activitiService.returnProcess();
//        return true;
//    }
//
//    @ApiOperation("发布流程实例")
//    @RequestMapping(value = "/startProcess",method = RequestMethod.GET)
//    public boolean startProcess() {
//
//        activitiService.startProcess();
//        return true;
//    }
//
//    @ApiOperation("部署流程")
//    @RequestMapping(value = "/deploymentProcess",method = RequestMethod.GET)
//    public void deploymentProcess() {
//
//        activitiService.deploymentProcess();
//    }
//
//    @ApiOperation("发送消息测试")
//    @RequestMapping(value = "/sendMessage",method = RequestMethod.GET)
//    public void test(@RequestParam String topic,@RequestParam String tags,String keys,@RequestParam String message) {
//        RocketMQProducer.send(topic, tags, keys,message);
//    }
//}
