package com.kirana.controller;

import com.kirana.dto.UserDTO;
import com.kirana.model.Role;
import com.kirana.model.Transaction;
import com.kirana.model.User;
import com.kirana.ratelimiter.RateLimiter;
import com.kirana.service.RoleService;
import com.kirana.service.TransactionService;
import com.kirana.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
//        user.setRoles(userDTO.getRoles());
        
        // Convert Set<String> (role names) to Set<Role>
        Set<Role> roles = userDTO.getRoles().stream()
            .map(roleName -> roleService.findOrCreateRole(roleName)) // Create or find Role by name
            .collect(Collectors.toSet());

        user.setRoles(roles);

        User createdUser = userService.registerUser(user);
        return ResponseEntity.ok(createdUser);
    }
    
   

}
