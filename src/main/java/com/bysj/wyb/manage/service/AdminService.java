package com.bysj.wyb.manage.service;

import com.bysj.wyb.manage.entity.Admin;
import com.bysj.wyb.manage.result.Result;

/**
 * @author admin
 */
public interface AdminService {

    Result logIn(Admin admin);

    Result findStudentListByClass(String classNumber);

    Result findTeacherList();

    Result delUser(String table,String uid);
}
