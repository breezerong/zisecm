package com.ecm.portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.core.entity.EcmParameter;
import com.ecm.icore.service.IParameterService;

/**
 * @ClassName  EcmParameterController   
 * @Description TODO(系统参数控件控制类)   
 * @author yaozhigang
 * @date 2018年7月3日 下午2:21:50  
 *
 */
@RestController
@RequestMapping("parameter")
public class EcmParameterController extends ControllerAbstract{
	
	private static final Logger logger = LoggerFactory.getLogger(EcmParameterController.class);
	
	@Autowired
	private IParameterService ecmParameterService;
	
	@RequestMapping("/queryEcmParameter")
	public EcmParameter queryEcmParameter(@RequestParam("name") String name) {
		logger.info("queryEcmParameter params name: {}", name);;
		return ecmParameterService.queryEcmParameter(getToken(),name);
	}
}
