package com.SunbaseAnkush.CustomerRelationshipManager.repository;

import com.SunbaseAnkush.CustomerRelationshipManager.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String userName);
}
