package com.guoyz.mall.controller;

import com.guoyz.mall.common.api.CommonResult;
import com.guoyz.mall.dto.UmsAdminLoginParm;
import com.guoyz.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description:
 * @Author:guoyz
 * @Date 2020/2/25 17:19
 */
@Controller
@Api(tags = "UmsAdminController", description="后台用户管理")
@RequestMapping("/admin")
@ResponseBody
public class UmsAdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    @Autowired
    private UmsAdminService umsAdminService;
    
    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResult login(@RequestBody @Valid UmsAdminLoginParm umsAdminLoginParm, BindingResult result){
        String token = umsAdminService.login(umsAdminLoginParm.getUsername(),umsAdminLoginParm.getPassword());
        if(token == null){
            return CommonResult.validateFailed("用户名或密码有误");
        }
        Map<String,String> tokenMap = new HashMap();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return CommonResult.success(tokenMap);
        
    }
}
