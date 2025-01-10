package com.gate.gateway.user.DTO;


import com.gate.gateway.user.entity.User;
import com.gate.gateway.user.entity.UserRole;

import java.util.Set;

public class UserResponse {
    protected String id;
    protected String name;


    public UserResponse(String id, String name) {
        this.id = id;
        this.name = name;

    }

    public UserResponse(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static UserResponse fromUser(User user){

        return new UserResponse(user.getId(),user.getName());
    }
}
