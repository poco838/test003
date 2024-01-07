package com.poco.test003.controller;

import com.poco.test003.entity.User;
import com.poco.test003.mapper.UserMapper;
import com.poco.test003.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@MapperScan("com.poco.test003.mapper")
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    //查询所有数据
    @GetMapping("/")
    public List<User> index(){
        User user =new User();
        user.setId(1);
        return  userMapper.findAll();
    }
    @Autowired
    private UserService userService;
    //新增和修改数据
    @PostMapping
    public Integer save(@RequestBody User user)
    {
        return   userService.save(user);
    }
    //删除数据
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id)
    {
        return   userMapper.deleteById(id);
    }
    //实现分页查询

    @GetMapping("/page")
    public Map<String,Object> findPage(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        pageNum = (pageNum-1) * pageSize;
        List<User> data= userMapper.selectPage(pageNum, pageSize);
        Integer pageTotal =   userMapper.selectPageTotal();               //查询总条数
        Map<String,Object> res=new HashMap<>();    //使用map<String,Object>2个参数定义返回，String为返回的总条数，Object
        // 为data对象，通过put将data和pageTotal返回，最后return返回res
        res.put("pageTotal",pageTotal);
        res.put("data",data);


        return res;
    }


}
