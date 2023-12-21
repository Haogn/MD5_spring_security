package com.ra.controller;

import com.ra.dto.request.UserLogin;
import com.ra.dto.request.UserRegister;
import com.ra.exception.CustomException;
import com.ra.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService ;

    // todo : register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegister userRegister) throws CustomException {
        return new ResponseEntity<>(userService.register(userRegister), HttpStatus.OK);
    }

    // todo : login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin) throws CustomException {
        return new ResponseEntity<>(userService.login(userLogin), HttpStatus.OK) ;
    }

}
