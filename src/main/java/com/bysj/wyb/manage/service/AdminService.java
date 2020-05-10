package com.bysj.wyb.manage.service;

import com.bysj.wyb.manage.entity.Admin;
import com.bysj.wyb.manage.entity.Teacher;
import com.bysj.wyb.manage.result.Result;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author admin
 */
public interface AdminService {

    Result logIn(Admin admin);

    Result findStudentListByClass(String classNumber);

    Result findTeacherList();

    Result delUser(String table,String uid);

    Result findStudentListByKey(String key);

    XSSFWorkbook createVoidExcelModel();

    Result createNewStudent(MultipartFile file,boolean low2007);

    Result createNewTeacher(Teacher teacher);
}
