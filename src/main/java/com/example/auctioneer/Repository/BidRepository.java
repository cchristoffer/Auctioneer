package com.example.auctioneer.Repository;

import com.example.auctioneer.Model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query(value = "SELECT * FROM `bid` INNER JOIN `auction` ON `bid`.`auction_auction_id` = `auction`.`auction_id` WHERE `auction`.`auction_id` =? ORDER BY `bid`.`bid_price` DESC", nativeQuery = true)
    List<Bid> findAllBidsByPrice(Long id);
}
