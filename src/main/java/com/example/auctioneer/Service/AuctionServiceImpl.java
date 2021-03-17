package com.example.auctioneer.Service;

import com.example.auctioneer.Model.Auction;
import com.example.auctioneer.Repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService{

    @Autowired
    AuctionRepository auctionRepository;

    @Override
    public void saveAuction(Auction auction) {
        auctionRepository.save(auction);
    }

    @Override
    public Page<Auction> findAllByDateAsc(PageRequest paging) {
        return auctionRepository.findAllByOrderByEndsAtAsc(paging);
    }

    @Override
    public Auction findAuctionById(Long id) {
        return auctionRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Auction> findAllByCategoryIdDateAsc(Long id, PageRequest page) {
        return auctionRepository.findAllByCategoryOrderByEndsAtAsc(id, page);
    }

    @Override
    public void deleteAuction(Long id) {
        auctionRepository.deleteById(id);
    }

    @Override
    public Page<Auction> searchAuctions(String search, PageRequest page) {
        return auctionRepository.searchAuctions(search, page);
    }

    @Override
    public List<Auction> endsToday(String date) {
        return auctionRepository.findAllEndsToday(date);
    }

    @Override
    public Page<Auction> findAllOrderBydate(PageRequest page) {
        return auctionRepository.findAllOrderBydate(page);
    }

}
