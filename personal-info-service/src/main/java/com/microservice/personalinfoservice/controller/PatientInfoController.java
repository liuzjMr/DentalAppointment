package com.microservice.personalinfoservice.controller;

import com.microservice.common.api.CommonResult;
import com.microservice.common.domain.UserDto;
import com.microservice.personalinfoservice.dto.PatientDto;
import com.microservice.personalinfoservice.service.PatientInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * 患者信息管理Controller
 */
@Api(tags = "PatientInfoController", description = "患者信息管理")
@RestController
@RequestMapping("/api/patient")
public class PatientInfoController {

    @Autowired
    private PatientInfoService patientInfoService;


    @ApiOperation("患者注册")
    @PostMapping("/register")
    public CommonResult register(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String phone,
                              @RequestParam String email,
                              @RequestParam String idNumber,
                              @RequestParam String name,
                              @RequestParam String gender,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd")
                              @RequestParam LocalDate birthday){

        Boolean result = patientInfoService.register(username, password, phone, email, idNumber, name, gender, birthday);
        if(result == Boolean.TRUE) {
            return CommonResult.success(null,"注册成功");
        }
        else {
            return CommonResult.failed("注册失败");
        }
    }

    // 患者登陆
    @PostMapping("/login")
    public CommonResult login(@RequestParam String username,
                              @RequestParam String password){

        return patientInfoService.login(username, password);
    }

    // 根据患者名获取患者信息
    @ApiOperation("根据患者名获取患者信息")
    @GetMapping("/{patientName}")
    public CommonResult getByName(@PathVariable String patientName){
        PatientDto patientDto = patientInfoService.getByName(patientName);
        if(patientDto == null) {
            return CommonResult.failed("查无此人");
        }
        return CommonResult.success(patientDto);
    }

    // 修改患者信息
    @PutMapping("/update")
    public CommonResult updatePatientInfo(@RequestParam String username,
                                          @RequestParam String phone,
                                          @RequestParam String email,
                                          @RequestParam String IDNumber,
                                          @RequestParam String name,
                                          @RequestParam String gender,
                                          @RequestParam String birthday){
        patientInfoService.updateInfo(username, phone, email, IDNumber, name, gender, birthday);
        return CommonResult.success(null,"修改成功");

    }

    // 修改密码
    @PutMapping("/update/password")
    public CommonResult updatePatientInfo(@RequestParam String username,
                                          @RequestParam String password){
        patientInfoService.updatePassword(username, password);
        return CommonResult.success(null,"修改成功");

    }

    // 根据患者名获取患者信息
    @ApiOperation("根据患者名获取患者信息")
    @GetMapping("/loadByUsername")
    public UserDto loadUserByUsername(@RequestParam String username){
        return patientInfoService.loadUserByUsername(username);
    }

}
