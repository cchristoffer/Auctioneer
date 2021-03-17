package com.example.auctioneer.Repository;

import com.example.auctioneer.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
