package com.tang.work.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface GroupDao {

    int deleteByPrimaryKey(int id);

    int insert(@Param("groupname")String groupname,@Param("userid") Integer userid);
    //int selectByName(@Param("groupname") String groupname,@Param("userid") Integer userid);

    List<Group> selectAll(@Param("groupname") String groupname,@Param("userid") Integer userid);

    Group selectById(int id);
}