package com.SunbaseAnkush.CustomerRelationshipManager.repository;

import com.SunbaseAnkush.CustomerRelationshipManager.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CustomerInfoRepository extends JpaRepository<Customer, String> {

}
