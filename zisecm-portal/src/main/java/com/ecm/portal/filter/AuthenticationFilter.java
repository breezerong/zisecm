package com.ecm.portal.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ecm.core.cache.manager.SessionManager;
import com.ecm.core.entity.ResultInfo;
import com.ecm.core.util.SysConfig;


/**
 * 登录认证过滤器
 * @author Haihong Rong
 * @date Jan 14, 2019
 */
@WebFilter(filterName = "ecmFilter",urlPatterns = "/*")
@Component
public class AuthenticationFilter implements Filter {

		
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
    	HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;

        //设置允许跨域的配置
        // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
        rep.setHeader("Access-Control-Allow-Origin", SysConfig.getAllowOrigin());
        // 允许的访问方法
        rep.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        rep.setHeader("Access-Control-Allow-Credentials", "true");
        rep.setHeader("Access-Control-Max-Age", "3600");
        rep.setHeader("Access-Control-Allow-Headers","token,Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("UTF-8");
        
    	if(req.getServletPath().indexOf("userLogin")>=0
    			||req.getServletPath().indexOf("ssoLogin")>=0
         		||req.getServletPath().indexOf("newDocument")>=0
         		||req.getServletPath().indexOf("getContent")>=0
         		||req.getServletPath().indexOf("getUserImage")>=0
         		||req.getServletPath().startsWith("/workflow")
         		){ 
         	 chain.doFilter(request, response);
         	 return;
         }
        ResultInfo resultInfo = new ResultInfo();

        boolean isFilter = false;

        String token = req.getHeader("token");//header方式
        if(token==null&&request.getParameter("token")!=null)
        {
        	token = req.getParameter("token");
        }
        String method = ((HttpServletRequest) request).getMethod();
        
        if (method.equals("OPTIONS")) {
        	rep.setStatus(HttpServletResponse.SC_OK);
        }else{
            if (null == token || token.isEmpty()) {
                resultInfo.setCode(SysConfig.getUnauthorized());
                resultInfo.setMsg("用户授权认证没有通过!客户端请求参数中无token信息");
            } else {
            	//暂时使用Session超时
//            	if(req.getSession().getAttribute("ECMUserToken")==null) {
//            		resultInfo.setCode(SysConfig.getUnauthorized());
//                    resultInfo.setMsg("token超时");
//            	}
                if (SessionManager.getInstance().getSession(token) != null) {
                    resultInfo.setCode(SysConfig.getSucess());
                    resultInfo.setMsg("用户授权认证通过!");
                    isFilter = true;
                } else {
                    resultInfo.setCode(SysConfig.getUnauthorized());
                    resultInfo.setMsg("用户授权认证没有通过!客户端请求参数token信息无效");
                }
            }
            if (resultInfo.getCode() == SysConfig.getUnauthorized()) {// 验证失败
                PrintWriter writer = null;
                OutputStreamWriter osw = null;
                try {
                    osw = new OutputStreamWriter(response.getOutputStream(),
                            "UTF-8");
                    writer = new PrintWriter(osw, true);
                    String jsonStr = JSON.toJSONString(resultInfo);
                    writer.write(jsonStr);
                    writer.flush();
                    writer.close();
                    osw.close();
                } catch (Exception ex)
                {
                	ex.printStackTrace();
                } finally {
                    if (null != writer) {
                        writer.close();
                    }
                    if (null != osw) {
                        osw.close();
                    }
                }
                return;
            }

            if (isFilter) {
            	chain.doFilter(request, response);
            }
        }
 
    }
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
    
}