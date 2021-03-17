package com.example.auctioneer.Service;

import com.example.auctioneer.Model.Users;
import com.example.auctioneer.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public void save(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public Users findByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public Page<Users> findAll(PageRequest page) {
        return userRepository.findAll(page);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Users findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
