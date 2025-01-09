package com.gate.gateway.user.service;

import com.gate.gateway.user.DTO.CreateUserRequest;
import com.gate.gateway.user.entity.User;
import com.gate.gateway.user.entity.UserRole;
import com.gate.gateway.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public User createUser(CreateUserRequest createUserRequest, Set<UserRole> roles){
        User user= new User(createUserRequest.getName(),passwordEncoder.encode(createUserRequest.getPassword()),roles);

        return userRepository.save(user);

    }

    public User createUserWithUserRole(CreateUserRequest createUserRequest){
        return createUser(createUserRequest, Set.of(UserRole.USER));

    }
    public User createUserWithAdminRole(CreateUserRequest createUserRequest){
        return createUser(createUserRequest, Set.of(UserRole.ADMIN));

    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public Optional<User> findById(String id){
        return userRepository.findById(id);
    }
    public Optional<User> findByUsername(String username){
        return  userRepository.findFirstByName(username);
    }
    public  Optional<User> edit(User user){
        return userRepository.findById(user.getId())
                .map( u ->{
                    u.setName(user.getName());
                    u.setPassword(user.getPassword());
                    return userRepository.save(u);
                });

    }

    public void deleteById(String id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
    }

}
