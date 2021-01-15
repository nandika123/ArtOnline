package com.ArtOnline.ArtOnline.service;


import com.ArtOnline.ArtOnline.model.User;
import com.ArtOnline.ArtOnline.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user.getFirst_name(), user.getLast_name(), user.getEmail_id(), user.getPassword(),"user");
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
