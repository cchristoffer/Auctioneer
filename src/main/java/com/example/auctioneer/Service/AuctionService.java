package com.example.auctioneer.Service;

import com.example.auctioneer.Model.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AuctionService {
    void saveAuction(Auction auction);
    Page<Auction> findAllByDateAsc(PageRequest paging);
    Auction findAuctionById(Long id);
    Page<Auction> findAllByCategoryIdDateAsc(Long id, PageRequest page);
    void deleteAuction(Long id);
    Page<Auction> searchAuctions(String search, PageRequest page);
    List<Auction> endsToday(String date);
    Page<Auction> findAllOrderBydate(PageRequest page);
}
