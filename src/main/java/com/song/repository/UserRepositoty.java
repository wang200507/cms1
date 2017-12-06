package com.song.repository;

import com.song.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Song on 2017/2/15.
 * User表操作接口
 */
@Repository
public interface UserRepositoty extends JpaRepository<User,Long>{

    @Query("select t from User t where t.userName = :name")
    User findByUserName(@Param("name") String name);

    @Query(value = "select t.* from sys_users t where t.user_code = ?1",nativeQuery = true)
      User findUserByUserName(String name );

    @Query("select t from User t where 1=1 and t.userName like  CONCAT('%',:name,'%') ")
    Page<User> findByNameLike(@Param("name") String name, Pageable pageable);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User set passWord=:passWord where userCode=:userCode")
    int setNewPassword(@Param("passWord") String passWord, @Param("userCode") String userCode);

}
