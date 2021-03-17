package com.example.auctioneer.Model;

import javax.persistence.*;

@Entity
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidId;
    private Double bidPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    private Auction auction;
    @ManyToOne(fetch = FetchType.EAGER)
    private Users bidOwner;

    public Bid(Double bidPrice, Auction auction, Users bidOwner) {
        this.bidPrice = bidPrice;
        this.auction = auction;
        this.bidOwner = bidOwner;
    }

    public Bid() {

    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Users getBidOwner() {
        return bidOwner;
    }

    public void setBidOwner(Users bidOwner) {
        this.bidOwner = bidOwner;
    }
}
