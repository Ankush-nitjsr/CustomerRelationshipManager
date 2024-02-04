package com.SunbaseAnkush.CustomerRelationshipManager.service;

import com.SunbaseAnkush.CustomerRelationshipManager.entity.UserInfo;
import com.SunbaseAnkush.CustomerRelationshipManager.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByName(username);
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword())); //encode the password provided by user
        userInfoRepository.save(userInfo); // save user in database
        return "User added successfully";
    }

    public List<UserInfo> getAllUser() {
        return userInfoRepository.findAll();
    }

    public UserInfo getUser(Integer id) {
        return userInfoRepository.findById(id).get();
    }
}
