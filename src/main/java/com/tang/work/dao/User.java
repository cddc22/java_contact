package com.tang.work.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * T_user
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class User {
    private Integer userid;

    private String username;

    private String password;
}