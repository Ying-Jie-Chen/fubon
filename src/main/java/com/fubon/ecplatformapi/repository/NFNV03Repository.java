package com.fubon.ecplatformapi.repository;

import com.fubon.ecplatformapi.model.entity.NFNV03Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NFNV03Repository extends JpaRepository<NFNV03Entity, Long> {
    NFNV03Entity findPaymentByPolyno(String polyNo);
}
