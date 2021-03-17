package com.example.auctioneer.Observable;

import com.example.auctioneer.Model.Auction;
import com.example.auctioneer.Model.Bid;
import org.springframework.stereotype.Service;

import java.util.Observable;

@Service
public class AuctionPublisher extends Observable {
    public void publishNewBid(Bid bid){
        setChanged();
        notifyObservers(bid);
    }

    public void publishNewAuction(Auction auction){
        setChanged();
        notifyObservers(auction);
    }

    public void publishExpiredAuction(Auction auction){
        setChanged();
        notifyObservers(auction);
    }
}
