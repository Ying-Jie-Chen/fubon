package com.fubon.ecplatformapi.repository;

import com.fubon.ecplatformapi.model.entity.NFNV02Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface NFNV02Repository extends JpaRepository<NFNV02Entity, Long> {
    NFNV02Entity findUnpaidByPolyno(String polyNo);
}
