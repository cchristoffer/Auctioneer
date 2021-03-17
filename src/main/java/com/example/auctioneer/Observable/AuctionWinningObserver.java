package com.example.auctioneer.Observable;

import com.example.auctioneer.Model.Auction;
import com.example.auctioneer.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;

@Service
public class AuctionWinningObserver implements Observer {
    private Auction auction;

    @Autowired
    EmailService emailService;

    @Override
    public void update(Observable o, Object arg) {
        auction=(Auction)arg;
        emailService.sendWinnersEmailAuctioneer(auction);
        emailService.sendWinnersEmailBidder(auction);
    }
}
