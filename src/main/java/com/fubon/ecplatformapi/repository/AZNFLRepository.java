package com.fubon.ecplatformapi.repository;


import com.fubon.ecplatformapi.model.entity.AZNFLEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AZNFLRepository extends JpaRepository<AZNFLEntity, Long> {

    AZNFLEntity findByPostalCode(String postalCode);
}
