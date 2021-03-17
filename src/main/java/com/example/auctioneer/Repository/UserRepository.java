package com.example.auctioneer.Repository;

import com.example.auctioneer.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String name);
}
