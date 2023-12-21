package com.ra.controller;

import com.ra.service.IUserAdminService;
import com.ra.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private IUserAdminService userAdminService;

    @GetMapping
    public ResponseEntity<?> getAllUser(@RequestParam(defaultValue = "") String search,
                                        @PageableDefault(size = 2, page = 0, sort = "userName", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(userAdminService.getAllUserByAdmin(search, pageable ), HttpStatus.OK);
    }
}
