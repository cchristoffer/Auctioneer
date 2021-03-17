package com.example.auctioneer.Observable;

import com.example.auctioneer.Model.Bid;
import com.example.auctioneer.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;

@Service
public class BidObserver implements Observer {
    private Bid bid;

    @Autowired
    EmailService emailService;

    @Override
    public void update(Observable o, Object arg) {
        bid=(Bid)arg;
        emailService.sendBidEmail(bid);
    }
}
