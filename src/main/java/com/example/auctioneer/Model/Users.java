package com.example.auctioneer.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String username;
    private String password;
    private String name;
    private String phoneNr;
    private String address;
    private String postNr;
    private String role;
    private int enabled;
    @OneToMany(
            mappedBy = "auctionOwner",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Auction> auctions = new ArrayList<>();
    @OneToMany(
            mappedBy = "bidOwner",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Bid> bids = new ArrayList<>();

    public Users() {
    }

    public Users(String username, String password, String name, String phoneNr, String address, String postNr) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNr = phoneNr;
        this.address = address;
        this.postNr = postNr;
        this.role = "ROLE_USER";
        this.enabled = 1;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostNr() {
        return postNr;
    }

    public void setPostNr(String postNr) {
        this.postNr = postNr;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public String getRole() {
        return role;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}
