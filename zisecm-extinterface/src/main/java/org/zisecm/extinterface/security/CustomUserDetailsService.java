package org.zisecm.extinterface.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecm.common.util.SecureUtils;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.UserEntity;
import com.ecm.core.service.MenuService;
import com.ecm.core.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
 
/**
 * 认证和授权
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserService userService;
 
    @Autowired
    private MenuService menuService;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        
        if (username.equals("admin")) {
//            UserEntity admin = new UserEntity();
            EcmUser user= userService.getObjectByLoginName("", "admin");
            
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
            grantedAuthorities.add(grantedAuthority);
            return new User(user.getLoginName(), user.getPassword(), grantedAuthorities);
        }
        else {
        	throw new UsernameNotFoundException("账号不允许登录！");
        }
    }
 
 
}

