package com.example.auctioneer.Model;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endsAt;
    private Double startingPrice;
    private Boolean active;
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "auction",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Bid> bids = new ArrayList<>();
    @OneToOne(fetch = FetchType.EAGER)
    private Item item;
    @ManyToOne(fetch = FetchType.EAGER)
    private Users auctionOwner;


    public Auction() {

    }

    public Double getCurrentPrice(){
        if(this.bids.size()>0){
            return this.bids.get(bids.size()-1).getBidPrice();
        }
        return this.startingPrice;
    }

    public Bid getHighestBid(){
        if(this.bids.size()>0){
            return this.bids.get(bids.size()-1);
        }
        return null;
    }

    public LocalDateTime getTimeRemaining(){
        long millis=System.currentTimeMillis();
        long endsMillis = this.endsAt.getTime();

        LocalDateTime date = Instant.ofEpochMilli(endsMillis - (millis / 60 / 60 / 24)).atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(date);
        return date;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }


    public Double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Users getAuctionOwner() {
        return auctionOwner;
    }

    public void setAuctionOwner(Users auctionOwner) {
        this.auctionOwner = auctionOwner;
    }
}
