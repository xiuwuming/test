package com.book.bus.service.impl;

import com.book.bus.domain.User;
import com.book.bus.mapper.UserMapper;
import com.book.bus.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 追风
 * @since 2019-12-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean verificationUser(String username) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user=userMapper.selectOne(queryWrapper);
        if (null!=user){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean verificationUser(String username, String password) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        User user=userMapper.selectOne(queryWrapper);
        if (null!=user){
            return true;
        }else {
            return false;
        }

    }
}
