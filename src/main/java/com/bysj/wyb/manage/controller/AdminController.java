package com.bysj.wyb.manage.controller;


import com.bysj.wyb.manage.entity.Admin;
import com.bysj.wyb.manage.entity.Teacher;
import com.bysj.wyb.manage.mapper.AdminMapper;
import com.bysj.wyb.manage.result.HandleResult;
import com.bysj.wyb.manage.result.Result;
import com.bysj.wyb.manage.service.AdminService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @RequestMapping(value = "/findStudentListByKey")
    public Result findStudentListByKey(@RequestBody Map<String,String> resBody){
        return adminService.findStudentListByKey(resBody.get("key"));
    }

    @RequestMapping(value = "/createStuModel")
    public void createNewExcel(HttpServletResponse response) throws IOException {
        XSSFWorkbook wb=adminService.createVoidExcelModel();
        String[] strNow=new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
        String excelName="学生模板"+strNow;
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream os = response.getOutputStream();
        //默认Excel名称
        response.setHeader("Content-disposition", "attachment;filename="+new String(excelName.getBytes("utf-8"),"ISO-8859-1" )+".xlsx");
        wb.write(os);
        os.flush();
        os.close();
    }

    @RequestMapping(value = "/createNewStudent")
    public Result CreatAccount(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        boolean low2007=false;
        String contentType = file.getContentType();
        String fileName=file.getOriginalFilename();

        if (file.isEmpty()) {
            return hr.outResultWithoutData("1","文件为空！");
        }
        //判断excel版本
        if(fileName.matches("^.+\\.(?i)(xls)$"))
        {
            low2007=true;
        }
        return adminService.createNewStudent(file,low2007);
    }

    @RequestMapping(value = "/createNewTeacher")
    public Result createNewTeacher(@RequestBody Teacher teacher){
        return adminService.createNewTeacher(teacher);
    }
}
