package com.ra.dto.response;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private Long id ;
    private String token ;
    private String email ;
    private String userName ;
    private Boolean status ;
    private Set<String> roles;
}
