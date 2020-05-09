package com.bysj.wyb.manage.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pd_teacher")
@Data
public class Teacher {

    int id;

    String uid;

    String name;

    String phone;

    String sex;

    String password;

    @Column(name = "forumname")
    String forumName;

    /**
     * 是否删除
     */
    int del;
}
