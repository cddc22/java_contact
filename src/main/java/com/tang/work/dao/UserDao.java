package com.tang.work.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author tang
 */
@Repository
public interface UserDao {

    int selectByName(@Param("username") String username);

    int deleteByprimaryKey(int id);

    int insert(@Param("username") String username, @Param("password") String password);

    User selectByPrimaryKey(String username,String password);

    int updateByPrimaryKeySelective(User record);


}