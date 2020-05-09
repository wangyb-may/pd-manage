package com.bysj.wyb.manage.controller;


import com.bysj.wyb.manage.entity.Admin;
import com.bysj.wyb.manage.mapper.AdminMapper;
import com.bysj.wyb.manage.result.HandleResult;
import com.bysj.wyb.manage.result.Result;
import com.bysj.wyb.manage.service.AdminService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author wangyb
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Resource
    AdminService adminService;

    @Resource
    AdminMapper adminMapper;

    HandleResult hr=new HandleResult();

    @RequestMapping(value = "/login")
    public Result logIn(@RequestBody Admin admin){
        return adminService.logIn(admin);
    }

    @RequestMapping(value = "/studentList")
    public Result studentList(@RequestBody Map<String, String> resBody){
        return adminService.findStudentListByClass(resBody.get("classNumber"));
    }

    @RequestMapping(value = "/teacherList")
    public Result teacherList(){
        return adminService.findTeacherList();
    }

    @RequestMapping(value = "/class")
    public Result findClass(){
        return hr.outResultWithData("0","查询成功",adminMapper.findClassNumber());
    }

    @RequestMapping(value = "/delUser")
    public Result delUser(@RequestBody Map<String,String> resBody){
        return adminService.delUser(resBody.get("table"),resBody.get("uid"));
    }
}
