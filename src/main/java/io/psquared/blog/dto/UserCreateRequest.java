package io.psquared.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

// TODO: automatically converts authorities to uppercase

// TODO: perform regex match for phoneNumber


public class UserCreateRequest {
    @NotBlank(message = "username is required")
    @Size(min = 2, message = "username must be longer than 2 chars")
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 2, message = "password must be longer than 2 chars")
    private String password;

    private String fullname;
    private String city;
    private String phoneNumber;

    List<String> authorities = new ArrayList<>();

    public UserCreateRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
