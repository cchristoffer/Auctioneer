package com.example.auctioneer.Service;


import com.example.auctioneer.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {
    void save(Users user);
    Users findByName(String name);
    Page<Users> findAll(PageRequest page);
    void deleteUser(Long id);
    Users findById(Long id);
}
