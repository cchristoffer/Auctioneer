package com.example.auctioneer.Factory;

import com.example.auctioneer.Model.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CategoryFactory {
    Page<Auction> getAllByCategory(String category, PageRequest page);
    String getTitle(String category);
}
