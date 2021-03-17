package com.example.auctioneer.Service;

import com.example.auctioneer.Model.Auction;
import com.example.auctioneer.Model.Bid;

public interface EmailService {
    public void sendBidEmail(Bid bid);
    public void sendAuctionEmail(Auction auction);
    public void sendWinnersEmailBidder(Auction auction);
    public void sendWinnersEmailAuctioneer(Auction auction);
}
