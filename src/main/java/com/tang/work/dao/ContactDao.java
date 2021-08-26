package com.tang.work.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tang
 */
@Mapper
public interface ContactDao {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Contact record);

    List<Contact> selectByPrimaryKey(@Param("name") String name);
    Contact selectById(@Param("id") Integer id);

    int countByGroup(int groupid);

    List<Contact> selectAll();

    List<Contact> selectByGroup(Integer id);

    int updateByPrimaryKeySelective(Contact record);
}