package com.ArtOnline.ArtOnline.service;

import com.ArtOnline.ArtOnline.model.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
