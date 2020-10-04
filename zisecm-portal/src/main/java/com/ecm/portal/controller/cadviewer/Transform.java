package com.ecm.portal.controller.cadviewer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.exception.AccessDeniedException;
import com.ecm.portal.controller.ControllerAbstract;
import com.google.gson.JsonObject;


@Controller
public class Transform extends ControllerAbstract {
	private static final Logger logger = LoggerFactory.getLogger(Transform.class);

	private static final long serialVersionUID = 1L;

	@RequestMapping(value = "/dc/getFlashParam") // , method = RequestMethod.POST PostMapping("/dc/getDocumentCount")
	@ResponseBody
	protected void getParam(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = "";
		if (request.getAttribute("id") != null) {
			id = request.getAttribute("id").toString();
		} else {
			id = request.getParameter("id");
		}
		logger.debug("id===================="+id);
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		//构造json数据
        /*构造JSON数据，返给前端页面。
        * taskId： 字符串，编号值自定，如不关注这个，给一个默认值即可
        * status： 整数，返回状态值，数字1
        * msg：  返回文字内容
         * resurl：  ocf文件的url地址，这里省略了转换过程，实际项目中需要先查询该图纸是否已经有对应的ocf文件了，如果已经存在，直接返回ocf的url地址即可，无需再次转换。
         *              如果没有ocf，需要调用样例1中的转换ocf代码，将图纸转换为ocf，然后将ocf文件的url赋值给resurl。
        * waterurl：水印文件，为空即可"
        * diagnosis：为空即可
         */
		JsonObject rs = new JsonObject();
		rs.addProperty("taskId", "1001");
		rs.addProperty("status", new Integer(1));
		rs.addProperty("msg", "OK");
		String ocfurl = null;
		try {
			ocfurl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()
			+"/dc/getContent?id="+id+"&token="+getToken()+"&format=ocf";
			logger.debug("url============="+ocfurl);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs.addProperty("resurl", ocfurl);
		rs.addProperty("waterurl", "");
		rs.addProperty("diagnosis", "");

		out.print(rs);
		out.close();

	}


}
