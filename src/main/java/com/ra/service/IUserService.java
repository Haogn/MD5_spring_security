package com.ra.service;

import com.ra.dto.request.UserLogin;
import com.ra.dto.request.UserRegister;
import com.ra.dto.response.UserResponse;
import com.ra.entity.Users;
import com.ra.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    void register(UserRegister userRegister) throws CustomException;

    UserResponse login (UserLogin userLogin ) throws CustomException;



}
