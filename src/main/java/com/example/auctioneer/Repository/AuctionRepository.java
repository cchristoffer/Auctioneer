package com.example.auctioneer.Repository;

import com.example.auctioneer.Model.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    @Query(value = "SELECT * FROM `auction` WHERE `active` =1 ORDER BY `ends_at` ASC", nativeQuery = true)
    Page<Auction> findAllByOrderByEndsAtAsc(PageRequest paging);

    @Query(value = "SELECT * FROM `auction` INNER JOIN `item` ON `auction`.`item_item_id` = `item`.`item_id` WHERE `ref_category_id` =? AND `auction`.`active` =1 ORDER BY `auction`.`ends_at` ASC", nativeQuery = true)
    Page<Auction> findAllByCategoryOrderByEndsAtAsc(Long id, PageRequest paging);

    @Query(value = "SELECT * FROM `auction` INNER JOIN `item` ON `auction`.`item_item_id` = `item`.`item_id` WHERE `item`.`name` LIKE %?1% OR `item`.`description` LIKE %?1%", nativeQuery = true)
    Page<Auction> searchAuctions(String search, PageRequest paging);

    @Query(value = "SELECT * FROM `auction` WHERE `active` =1 AND `auction`.`ends_at` LIKE %?1%", nativeQuery = true)
    List<Auction> findAllEndsToday(String date);

    @Query(value = "SELECT * FROM `auction` ORDER BY `ends_at` ASC", nativeQuery = true)
    Page<Auction> findAllOrderBydate(PageRequest paging);
}
