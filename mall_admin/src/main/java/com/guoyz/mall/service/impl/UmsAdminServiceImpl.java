package com.guoyz.mall.service.impl;

import com.guoyz.mall.bo.AdminUserDetails;
import com.guoyz.mall.mapper.UmsAdminMapper;
import com.guoyz.mall.mapper.UmsAdminRoleRelationMapper;
import com.guoyz.mall.model.UmsAdmin;
import com.guoyz.mall.model.UmsAdminExample;
import com.guoyz.mall.model.UmsPermission;
import com.guoyz.mall.security.util.JwtTokenUtil;
import com.guoyz.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Description:
 * @Author:guoyz
 * @Date 2020/2/25 15:27
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    
  
    @Autowired
    private UmsAdminMapper umsAdminMapper;
    
    @Autowired
    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
            
    
    @Override
    public String login(String username, String password) {
        String token = null;
        UserDetails userDetails = loadAdminByUsername(username);
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("密码不正确");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> list = umsAdminMapper.selectByExample(umsAdminExample);
        if(list != null && list.size() >0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserDetails loadAdminByUsername(String username) {
        UmsAdmin umsAdmin = getAdminByUsername(username);
        if(umsAdmin != null){
          List<UmsPermission> umsPermissionList = getPermissionList(umsAdmin.getId());
          return new AdminUserDetails(umsAdmin,umsPermissionList);
        }
        throw new UsernameNotFoundException("用户名或密码有误");
    }

    @Override
    public List<UmsPermission> getPermissionList(Long userId) {
       return  umsAdminRoleRelationMapper.getPermissionList(userId);
    }

    
}
