package com.microservice.personalinfoservice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.microservice.common.api.CommonResult;
import com.microservice.common.domain.UserDto;
import com.microservice.personalinfoservice.dto.AdminDto;
import com.microservice.personalinfoservice.entity.AdminInfo;
import com.microservice.personalinfoservice.mapper.AdminInfoMapper;
import com.microservice.personalinfoservice.service.AdminInfoService;
import com.microservice.personalinfoservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminInfoServiceImpl implements AdminInfoService {

    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @Autowired
    private AuthService authService;

    @Override
    public Boolean register(String username, String password, String phone, String email, String hospital,
                            String name, Integer jobNumber) {
        // 查询是否已有该用户
        if (getByName(username) == null){
            return Boolean.FALSE;
        }

        // 没有对该用户进行添加操作
        int result = adminInfoMapper.insert(new AdminInfo(username, password, phone, email, hospital,
                name, jobNumber));
        if(result == 1){
            return Boolean.TRUE;
        }
        else {
            return Boolean.FALSE;
        }
    }

    @Override
    public CommonResult login(String username, String password) {
        if(StrUtil.isEmpty(username)||StrUtil.isEmpty(password)){
            return CommonResult.failed("用户名或密码不能为空！");
        }
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "client_patient");
        params.put("client_secret","patient");
        params.put("grant_type","password");
        params.put("username",username);
        params.put("password",password);
        return CommonResult.success(authService.getAccessToken(params));
    }

    @Override
    public AdminDto getByName(String username) {
        AdminInfo adminInfo = getAllInfoByName(username);

        if(adminInfo != null) {
            AdminDto adminDto = new AdminDto();
            BeanUtil.copyProperties(adminInfo, adminDto);
            return adminDto;
        }
        return null;
    }

    @Override
    public AdminInfo getAllInfoByName(String username) {
        QueryWrapper<AdminInfo> adminInfoQueryWrapper = new QueryWrapper<>();
        adminInfoQueryWrapper.eq("username", username);

        return adminInfoMapper.selectOne(adminInfoQueryWrapper);
    }

    @Override
    public Boolean updateInfo(String username, String phone, String email, String hospital, String name,
                              Integer jobNumber) {
        UpdateWrapper<AdminInfo> adminInfoUpdateWrapper = new UpdateWrapper<>();
        adminInfoUpdateWrapper.eq("username",username)
                .set("phone", phone)
                .set("email", email)
                .set("hospital", hospital)
                .set("name", name)
                .set("jobNumber", jobNumber);

        int result = adminInfoMapper.update(null, adminInfoUpdateWrapper);

        if(result == 1){
            return Boolean.TRUE;
        }
        else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean updatePassword(String username, String password) {
        UpdateWrapper<AdminInfo> adminInfoUpdateWrapper = new UpdateWrapper<>();
        adminInfoUpdateWrapper.eq("username",username)
                .set("password", password);

        int result = adminInfoMapper.update(null, adminInfoUpdateWrapper);

        if(result == 1){
            return Boolean.TRUE;
        }
        else {
            return Boolean.FALSE;
        }
    }

    @Override
    public UserDto loadUserByUsername(String username) {
        AdminInfo adminInfo = getAllInfoByName(username);

        if(adminInfo != null) {
            UserDto userDto = new UserDto();
            BeanUtil.copyProperties(adminInfo, userDto);
            return userDto;
        }
        return null;
    }
}