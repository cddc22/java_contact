package com.tang.work.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface GroupDao {

    int deleteByPrimaryKey(int id);

    int insert(@Param("groupname")String groupname);
    int selectByName(@Param("groupname") String groupname);

    List<Group> selectAll();

    Group selectById(int id);
}