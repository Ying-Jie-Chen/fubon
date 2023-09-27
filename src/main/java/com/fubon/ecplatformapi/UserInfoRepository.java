package com.fubon.ecplatformapi;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String>{

    Optional<UserInfo> findByEmail(String email);
}
