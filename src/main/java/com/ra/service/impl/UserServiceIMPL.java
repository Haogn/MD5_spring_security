package com.ra.service.impl;

import com.ra.dto.request.UserLogin;
import com.ra.dto.request.UserRegister;
import com.ra.dto.response.UserResponse;
import com.ra.entity.RoleName;
import com.ra.entity.Roles;
import com.ra.entity.Users;
import com.ra.exception.CustomException;
import com.ra.mapper.UserMapper;
import com.ra.repository.IUserRepository;
import com.ra.security.jwt.JwtProvider;
import com.ra.security.user_principal.UserPrincipal;
import com.ra.service.IRoleService;
import com.ra.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceIMPL implements IUserService {
    @Autowired
    private IUserRepository userRepository ;

    @Autowired
    private IRoleService roleService ;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider ;

    @Autowired
    private AuthenticationProvider authenticationProvider ;

    @Autowired
    private UserMapper userMapper ;

    // todo : register
    @Override
    public void register(UserRegister userRegister) throws CustomException {
        // Kiểm tra xem email đã tồn tại chưa
        if (userRepository.existsByEmail(userRegister.getEmail())) {
            throw new CustomException("Email exists");
        }

        Set<Roles> roles = new HashSet<>();
        // Nếu không có quyền được truyền lên, mặc định là role user
        if (userRegister.getRoles() == null || userRegister.getRoles().isEmpty()) {
            roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        } else {
            // Xác định quyền dựa trên danh sách quyền được truyền lên
            userRegister.getRoles().forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                    case "user":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
                        break;
                    default:
                        try {
                            throw new CustomException("role not found");
                        } catch (CustomException e) {
                            throw new RuntimeException(e);
                        }
                }
            });
        }


         userRepository.save(Users.builder()
                        .email(userRegister.getEmail())
                        .userName(userRegister.getUserName())
                        .status(userRegister.getStatus())
                        .password(passwordEncoder.encode(userRegister.getPassword()))
                        .status(true)
                        .roles(roles)
                .build());
    }

    // todo : login
    @Override
    public UserResponse login(UserLogin userLogin) throws CustomException {
        Authentication authentication ;
        authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUserName(), userLogin.getPassword()));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal(); // todo : bao sao cua Users

        // Lấy thông tin người dùng từ Principal và kiểm tra trạng thái tài khoản
        if (!userPrincipal.getStatus()) {
            throw new CustomException("your account is blocked");
        }
        return UserResponse.builder()
                .token(jwtProvider.generateToken(userPrincipal))
                .userName(userLogin.getUserName())
                .email(userPrincipal.getEmail())
                .status(userPrincipal.getStatus())
                .roles(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }




}
