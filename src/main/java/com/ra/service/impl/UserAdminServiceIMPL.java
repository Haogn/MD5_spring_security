package com.ra.service.impl;

import com.ra.dto.response.UserResponse;
import com.ra.entity.RoleName;
import com.ra.entity.Users;
import com.ra.exception.CustomException;
import com.ra.mapper.UserMapper;
import com.ra.repository.IUserRepository;
import com.ra.service.IUserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserAdminServiceIMPL implements IUserAdminService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<UserResponse> getAllUserByAdmin(String search, Pageable pageable) {
        Page<Users> list;
        if (search.isEmpty()) {
            list = userRepository.findAll(pageable);
        } else {
            list = userRepository.findAllByUserNameContainingIgnoreCase(search, pageable);
        }
        return list.map(item -> userMapper.toUserResponse(item));
    }

    // todo : chuc nang lock tai khoan
    @Override
    public UserResponse changeStatusUser(Long id) throws CustomException {
        Users users = userRepository.findById(id).orElseThrow(() -> new CustomException("User not found"));
        users.setStatus(!users.getStatus());
        return userMapper.toUserResponse(userRepository.save(users));
    }
}
