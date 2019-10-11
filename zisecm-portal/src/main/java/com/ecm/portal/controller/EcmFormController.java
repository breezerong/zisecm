package com.ecm.portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.core.entity.EcmForm;
import com.ecm.icore.service.IFormService;

/**
 * @ClassName  EcmFormController   
 * @Description TODO(表单控件控制类)   
 * @author yaozhigang
 * @date 2018年6月29日 下午2:21:50  
 *
 */
@RestController
@RequestMapping("form")
public class EcmFormController extends ControllerAbstract{
	
	private static final Logger logger = LoggerFactory.getLogger(EcmFormController.class);
	
	@Autowired
	private IFormService ecmFormService;
	
	@RequestMapping("/queryEcmForm")
	public EcmForm queryEcmForm(@RequestParam("name") String typeName,@RequestParam("action") String action) {
		logger.info("queryEcmForm params typeName: {}, action: {}", typeName,action);;
		return ecmFormService.queryEcmForm(typeName,action);
	}
}
