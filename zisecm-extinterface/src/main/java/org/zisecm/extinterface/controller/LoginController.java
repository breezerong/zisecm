package org.zisecm.extinterface.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
 
@Controller
public class LoginController {
 
//    @Autowired
//    private AuthenticationManager myAuthenticationManager;
 
 
    @RequestMapping(value = "/userLogin")
    public String userLogin(HttpServletRequest request) {
		return null;
 
//        User userInfo = new User(username, password, authorities);
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
// 
//        userInfo.setUsername(username);
//        userInfo.setPassword(password);
// 
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
// 
// 
//        try{
//            //使用SpringSecurity拦截登陆请求 进行认证和授权
//            Authentication authenticate = myAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
// 
//            SecurityContextHolder.getContext().setAuthentication(authenticate);
//            //使用redis session共享
//            HttpSession session = request.getSession();
//            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
//        }catch (Exception e){
//            e.printStackTrace();
//            return "redirect:login-error?error=2";
//        }
// 
// 
//        return "redirect:index";
    }
 

}
