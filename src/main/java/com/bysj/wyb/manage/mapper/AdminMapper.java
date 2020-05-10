package com.bysj.wyb.manage.mapper;

import com.bysj.wyb.manage.entity.Admin;
import com.bysj.wyb.manage.entity.Student;
import com.bysj.wyb.manage.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    Admin logIn(Admin admin);

    List<Student> findStudentListByClass(String classNumber);

    List<Teacher> findTeacherList();

    List<Student> findClassNumber();

    int delUser(String table,String uid);

    List<Student> findStudentByKey(String key);

    int createNewStudent(Student student);

    int createNewTeacher(Teacher teacher);
}
