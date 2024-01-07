package com.poco.test003.service;

import com.poco.test003.entity.User;
import com.poco.test003.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public  int save(User user){
        if(user.getId() == null){        //判断user是否有id，如果id为空则为插入
        return     userMapper.insert(user);
        }else  //否则为更新user
       return  userMapper.update(user);
    }

}
