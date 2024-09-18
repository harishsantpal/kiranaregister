package com.kirana.service;

import com.kirana.model.User;

public interface UserService {
    User registerUser(User user);
    User findByUsername(String username);
}
