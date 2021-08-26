package com.tang.work.dao;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * t_contact
 * @author 
 */
@Data
@Repository
public class Contact implements Serializable {
    private Integer id;

    private String name;

    private String telephone;

    private String sex;

    private String wechat;

    private String address;

    private String email;

    private String birth;

    private Integer groupid;

    private Integer userid;

    private static final long serialVersionUID = 1L;
}