package com.ra.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLogin {
//    private String email;
    private String userName ;
    private String password ;
}
