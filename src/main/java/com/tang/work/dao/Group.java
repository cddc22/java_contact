package com.tang.work.dao;

import lombok.Data;

import java.io.Serializable;

/**
 * t_group
 * @author Tang
 */
@Data
public class Group implements Serializable {
    private Integer groupid;

    private String groupname;

    private static final long serialVersionUID = 1L;
    @Override
    public String toString(){
        return "组名："+getGroupname();
    }
}