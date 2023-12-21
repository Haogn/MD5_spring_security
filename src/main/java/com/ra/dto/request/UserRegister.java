package com.ra.dto.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRegister {
    private String email ;
    private String userName ;
    private String password ;
    private Boolean status ;
    private List<String> roles;
}
