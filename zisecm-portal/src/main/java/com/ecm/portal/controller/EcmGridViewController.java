package com.ecm.portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.core.entity.EcmGridView;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.icore.service.IGridViewService;

/**
 * @ClassName  EcmGridViewController   
 * @Description TODO(列表服务控制类)   
 * @author yaozhigang
 * @date 2018年6月29日 下午2:21:50  
 *
 */
@RestController
@RequestMapping("gridview")
public class EcmGridViewController extends ControllerAbstract{
	private static final Logger logger = LoggerFactory.getLogger(EcmGridViewController.class);

	@Autowired
	private IGridViewService ecmGridViewService;
	
	@RequestMapping("/queryGridView")
	public EcmGridView queryGridView(@RequestParam("name") String gridName) {
		logger.info("queryGridView params name: {}",gridName);
		try {
			return ecmGridViewService.queryGridView(getToken(),gridName);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
