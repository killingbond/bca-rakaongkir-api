package com.cus.jastip.bca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cus.jastip.bca.domain.ProvinceModel;

@Repository
public interface ProvinceRepository extends JpaRepository<ProvinceModel, Integer> {

}
