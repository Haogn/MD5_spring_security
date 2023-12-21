package com.ra.repository;

import com.ra.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserName(String userName) ;

    boolean existsByEmail(String email);

    Boolean existsByUserName(String userName) ;

    Page<Users> findAllByUserNameContainingIgnoreCase(String userName, Pageable pageable);

}
