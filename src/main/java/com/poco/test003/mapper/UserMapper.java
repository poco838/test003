package com.poco.test003.mapper;

import com.poco.test003.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Mapper
@Repository
@Component
public interface UserMapper {
//查询所有数据
    @Select("select  * from user")
    List<User> findAll();


@Insert("INSERT INTO test.user (username, password, nickname, email, phone, address,create_time) " +
        "VALUES (#{username}, #{password}, #{nickname}, #{email}, #{phone}, #{address},NOW())")
    int  insert(User User);


    int update(User user);

    @Delete("delete from user where id =#{id}")
    Integer deleteById(@Param("id") Integer id);

    @Select("select * from user limit #{pageNum},#{pageSize}")
    List<User> selectPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
     @Select("select count(*) from  user")
    Integer selectPageTotal();
}
