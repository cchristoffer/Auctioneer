package com.example.auctioneer.Repository;

import com.example.auctioneer.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
