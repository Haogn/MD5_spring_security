package com.ra.service;

import com.ra.dto.response.UserResponse;
import com.ra.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserAdminService {
    Page<UserResponse> getAllUserByAdmin( String search ,Pageable pageable);
    UserResponse changeStatusUser(Long id) throws CustomException;
}
