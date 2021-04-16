package com.ecm.portal.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.SessionManager;
import com.ecm.core.entity.ResultInfo;
import com.ecm.core.util.SysConfig;
import com.ecm.icore.service.IEcmSession;


/**
 * 登录认证过滤器
 * @author Haihong Rong
 * @date Jan 14, 2019
 */
@WebFilter(filterName = "ecmFilter",urlPatterns = "/*")
@Component
public class AuthenticationFilter implements Filter {

	private static final String MULTIPART_HEADER = "Content-type";
		
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
         		||req.getServletPath().indexOf("/workflow")>=0
         		||req.getServletPath().indexOf("/admin/validataAdminToken")>=0
         		||req.getServletPath().indexOf("/addAudit")>=0
         		||req.getServletPath().indexOf("/revokeAcl2")>=0
         		||req.getServletPath().indexOf("/archive/todoCount")>=0
         		||req.getServletPath().indexOf("/swagger")>=0
         		||req.getServletPath().indexOf("/api-docs")>=0
         		||req.getServletPath().indexOf("/webjars/springfox-swagger-ui/")>=0
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
            	IEcmSession session = SessionManager.getInstance().getSession(token);
                if ( session != null) {
                	session.getCurrentUser().setUpdateTime(new Date());
                	try {
						//SessionManager.getInstance().refresh(token);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
            	//上传文件白名单校验
            	if(validateFileUpload(req))
            	{
            		chain.doFilter(request, response);
            	}else {
            		response.getWriter().write("{\"code\":0,\"msg\":\""+getExtendWhiteList()+"\"}");
            	}
            }
        }
 
    }
	
	private boolean validateFileUpload(HttpServletRequest request) {
		Boolean multipart = request.getHeader(MULTIPART_HEADER) != null && request
                .getHeader(MULTIPART_HEADER).startsWith("multipart/form-data");
        // 如果是上传文件
        if (multipart)
        {
        	String whiteList = getExtendWhiteList();
        	if(whiteList==null)
        	{
        		return true;
        	}
            try
            {
            	Collection<Part> parts = request.getParts();
            	for (Iterator<Part> iterator = parts.iterator(); iterator.hasNext();) {
            		Part part = iterator.next();  
					String name = part.getSubmittedFileName();
					if(name != null && name.indexOf(".")>0) {
						String ext = name.substring(name.indexOf(".")+1, name.length())+";";
						if(whiteList.indexOf(ext)<0) {
							return false;
						}
					}
				}
            }
            catch(Exception ex) {
            	ex.printStackTrace();
            }
        }
        return true;
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	//扩展名白名单，最后一定要加英文分号
	private String getExtendWhiteList() {
		if(CacheManagerOper.getEcmParameters().get("EnableExtendWhiteList")==null 
				|| "false".equalsIgnoreCase(CacheManagerOper.getEcmParameters().get("EnableExtendWhiteList").getValue())) {
			return null;
		}
		if(CacheManagerOper.getEcmParameters().get("ExtendWhiteList")!=null){
			return CacheManagerOper.getEcmParameters().get("ExtendWhiteList").getValue();
		}
		return "doc;docx;xls;xlsx;ppt;pptx;zip;rar;pdf;jpg;jpeg;dwg;tiff;tif;bmp;gif;png;txt;wps;rtf";
	}
    
}