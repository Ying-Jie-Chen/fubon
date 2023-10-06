package com.fubon.ecplatformapi.repository;

import com.fubon.ecplatformapi.model.entity.LicenseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LicenseInfoRepository extends JpaRepository<LicenseInfo, String> {
    List<LicenseInfo> findByLicenseIdAndCodeFlagNotIn(String licenseId, List<String> codeFlags);
}

