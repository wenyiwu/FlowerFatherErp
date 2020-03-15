package com.flower.father.controller;

import com.flower.father.model.param.LoginParam;
import com.flower.father.model.response.LoginResponse;
import com.flower.father.model.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author eiven
 */
@CrossOrigin
@Controller
@RequestMapping("/api/v1")
public class UserController {

    @RequestMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> login(LoginParam param){
        LoginResponse response = new LoginResponse();
        response.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInVzZXJfaWQiOiJicGthMjIxOTRjZzQwZ2I0dnZyMCIsInBlcl9hZGRyIjoiMTI3LjAuMC4xIiwicm9sZXMiOlsiYWRtaW4iXSwiZXhwIjoxNTg0MjgzOTQyLCJpYXQiOjE1ODQwMjQ3NDIsImlzcyI6Im1vZ3V0b3UifQ.p7iWXhSZLZYYT-4SpwUuvDs8hP0O5pfTf-0DBlf6iyQ");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping("/logout")
    @ResponseBody
    public void logout(){
        return;
    }

    @RequestMapping("/user")
    @ResponseBody
    public ResponseEntity<UserResponse> user(){
        UserResponse response = new UserResponse();
        response.setName("FlowerFather");
        response.setRoles("[\"admin\"]");
        response.setTel("123");
        response.setPosition("花店");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
