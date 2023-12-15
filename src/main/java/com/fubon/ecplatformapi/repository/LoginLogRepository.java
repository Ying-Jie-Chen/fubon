package com.fubon.ecplatformapi.repository;

import com.fubon.ecplatformapi.model.entity.LoginLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLogEntity, Long> {
}
