package com.guoyz.mall.service;

import com.guoyz.mall.model.UmsAdmin;
import com.guoyz.mall.model.UmsPermission;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author dell
 */
public interface UmsAdminService {
    
    public String login(String username,String password);
    
    public UmsAdmin getAdminByUsername(String usernmae);
    
    public UserDetails loadAdminByUsername(String username);
    
    public List<UmsPermission> getPermissionList(Long userId);
}
