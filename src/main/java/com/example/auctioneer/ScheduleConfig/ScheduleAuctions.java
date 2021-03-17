package com.example.auctioneer.ScheduleConfig;

import com.example.auctioneer.Model.Auction;
import com.example.auctioneer.Observable.AuctionPublisher;
import com.example.auctioneer.Observable.AuctionWinningObserver;
import com.example.auctioneer.Service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ScheduleAuctions {
    @Autowired
    AuctionService auctionService;

    @Autowired
    AuctionPublisher auctionPublisher;

    @Autowired
    AuctionWinningObserver auctionWinningObserver;

    @Scheduled(cron = "0 * * * * ?")
    public void cronJob() throws Exception{
        try{
        LocalDate date = LocalDate.now();
        String strDate = date.toString();

        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.toString().replace("T", " ");
        String newStrNow = nowStr.substring(0, 19);
        List<Auction> endingSoon = auctionService.endsToday(strDate);
        LocalDateTime ld2 = LocalDateTime.parse(newStrNow, sdf);
        System.out.println("************\nCHECKING AUCTIONS\n************");
        for (int i = 0; i < endingSoon.size(); i++) {
            LocalDateTime ld1 = endingSoon.get(i).getEndsAt().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            if (ld2.isAfter(ld1)){
                    Auction auction = endingSoon.get(i);
                    auction.setActive(false);
                    auctionService.saveAuction(auction);
                    auctionPublisher.addObserver(auctionWinningObserver);
                    auctionPublisher.publishExpiredAuction(auction);
                    System.out.println("Ended auction "+auction.getAuctionId());
                    }
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
