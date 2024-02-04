package com.SunbaseAnkush.CustomerRelationshipManager.model;

import com.SunbaseAnkush.CustomerRelationshipManager.entity.UserInfo;
import lombok.Data;

@Data
public class LoginCredentials {
    private String login_id;
    private String password;
}
