package com.gate.gateway.user.DTO;

import com.gate.gateway.user.entity.User;

public class JwtUserResponse extends UserResponse {
    private  String token;

    public JwtUserResponse(String id, String name, String token) {
        super(id, name);
        this.token = token;
    }

    public JwtUserResponse(UserResponse userResponse){
        id = userResponse.getId();
        name = userResponse.getName();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static  JwtUserResponse of (User user, String token){
        JwtUserResponse result = new JwtUserResponse(UserResponse.fromUser(user));
        result.setToken(token);
        return result;
    }

}
