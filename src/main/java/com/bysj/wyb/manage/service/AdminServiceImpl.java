package com.bysj.wyb.manage.service;

import com.bysj.wyb.manage.entity.Admin;
import com.bysj.wyb.manage.entity.Student;
import com.bysj.wyb.manage.mapper.AdminMapper;
import com.bysj.wyb.manage.result.HandleResult;
import com.bysj.wyb.manage.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author admin
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Resource
    AdminMapper adminMapper;

    HandleResult hr=new HandleResult();

    @Override
    public Result logIn(Admin admin) {
        try {
            Admin exsistAdmin=new Admin();
            exsistAdmin=adminMapper.logIn(admin);
            if(admin.getPassword().equals(exsistAdmin.getPassword())){
                return hr.outResultWithData("0","登陆成功",exsistAdmin);
            }else {
                return hr.outResultWithoutData("1","登录名或密码不正确");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return hr.outResultWithoutData("1","账号不存在!");
        }
    }

    @Override
    public Result findStudentListByClass(String classNumber) {
        return hr.outResultWithData("0","查询成功",adminMapper.findStudentListByClass(classNumber));
    }

    @Override
    public Result findTeacherList() {
        return hr.outResultWithData("0","查询成功",adminMapper.findTeacherList());
    }

    @Override
    public Result delUser(String table, String uid) {
        try{
            if(1==adminMapper.delUser(table,uid)){
                return hr.outResultWithoutData("0","该账号已经停用");
            }else{
                return hr.outResultWithoutData("0","未知错误");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return hr.outResultWithoutData("0","未知错误");
        }
    }
}
