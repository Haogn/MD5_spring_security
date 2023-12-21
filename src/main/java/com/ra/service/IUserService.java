package com.ra.service;

import com.ra.dto.request.UserLogin;
import com.ra.dto.request.UserRegister;
import com.ra.dto.response.UserResponse;
import com.ra.entity.Users;
import com.ra.exception.CustomException;

public interface IUserService {
    Users register(UserRegister userRegister) throws CustomException;

    UserResponse login (UserLogin userLogin ) throws CustomException;

}
