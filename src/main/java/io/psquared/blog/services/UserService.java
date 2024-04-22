package io.psquared.blog.services;

import io.psquared.blog.dto.UserCreateRequest;
import io.psquared.blog.dto.UserResponse;
import io.psquared.blog.entity.Authority;
import io.psquared.blog.entity.User;
import io.psquared.blog.exceptions.type.DupeFound;
import io.psquared.blog.repository.AuthorityRepository;
import io.psquared.blog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthorityRepository authorityRepository
        ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        Optional<User> userExist = userRepository.findByUsername(userCreateRequest.getUsername());

        if(userExist.isPresent()){
            throw new DupeFound(userCreateRequest.getUsername() + " already exists");
        }

        User newUser = new User();
        newUser.setUsername(userCreateRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));

        if(userCreateRequest.getCity() != null){
            newUser.setCity(userCreateRequest.getCity());
        }

        if(userCreateRequest.getFullname() != null){
            newUser.setFullname(userCreateRequest.getFullname());
        }

        if(userCreateRequest.getPhoneNumber() != null){
            newUser.setPhoneNumber(userCreateRequest.getPhoneNumber());
        }

        // iterate over authorities and create if it doesn't exist
        for (String authority : userCreateRequest.getAuthorities()) {
            Optional<Authority> authorityExists = authorityRepository.findByName(authority);

            if(authorityExists.isEmpty()){
                Authority authorityObj = new Authority(authority);
                newUser.addAuthority(authorityObj);
            } else {
                newUser.addAuthority(authorityExists.get());
            }
        }

        User savedUser = userRepository.save(newUser);

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(savedUser, userResponse);

        System.out.println(savedUser.getAuthorities());

        // create payload for JWT token
//        HashMap<String, Object> payload = new HashMap<>();
//        payload.put("city", "blr");
//        payload.put("roles", new String[] {"role1", "role2"});

//        String token = JwtUtil.generateToken(userRequest.getUsername(), payload);
//        userResponse.setToken(token);

        return userResponse;
    }
}
