package com.bysj.wyb.manage.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "pd_student")
@Entity
public class Student implements Serializable {

    @Id
    /**
     * 学生uid
     */
    String uid;

    /**
     * 登录名
     */
    @Column(name = "nickname")
    String nickName;

    /**
     * 登录密码
     */
    String password;

    /**
     * 姓名
     */
    String name;

    /**
     * 性别
     */
    String sex;

    /**
     * 学生所在班级号
     */
    @Column(name="classnumber")
    String classNumber;

    /**
     * 年龄
     */
    int age;

    /**
     * 手机号码
     */
    String mobile;

    /**
     * 头像地址
     */
    @Column(name="imageurl")
    String imageUrl;

    /**
     * 是否注册论坛
     */
    int signForum;

    /**
     * 论坛昵称
     */
    @Column(name = "forumname")
    String forumName;

    @Column(name="isdelete")
    int isDelete;
}
