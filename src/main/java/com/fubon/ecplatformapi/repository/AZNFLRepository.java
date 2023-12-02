package com.fubon.ecplatformapi.repository.fgisdb;

import com.fubon.ecplatformapi.model.entity.fgisdb.AZNFLEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AZNFLRepository extends JpaRepository<AZNFLEntity, Long> {

    AZNFLEntity findByPostalCode(String postalCode);
}
