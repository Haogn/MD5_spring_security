package com.ra.security.user_principal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ra.entity.Users;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class  UserPrincipal implements UserDetails {
    private Long id;

    private String email;

    private String username;

    @JsonIgnore
    private String password;

    private Boolean status;

    private Collection<? extends GrantedAuthority> authorities;

    // Phương thức tạo đối tượng UserPrincipal từ đối tượng Users
    public static UserDetails build(Users users) {
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .id(users.getId())
                .email(users.getEmail())
                .username(users.getUserName())
                .password(users.getPassword())
                .status(users.getStatus())
                .authorities(users.getRoles().stream().map(item -> new SimpleGrantedAuthority(item.getRoleName().name())).collect(Collectors.toList()))
                .build();
        return userPrincipal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
