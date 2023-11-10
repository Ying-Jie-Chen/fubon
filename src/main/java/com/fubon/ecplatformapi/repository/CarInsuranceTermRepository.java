package com.fubon.ecplatformapi.repository;

import com.fubon.ecplatformapi.model.entity.CarInsuranceTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarInsuranceTermRepository extends JpaRepository<CarInsuranceTerm, String> {
    List<CarInsuranceTerm> findByTermInsCodeAndTermInsApiParam1In(String termInsCode, List<String> termInsApiParam1);

}
