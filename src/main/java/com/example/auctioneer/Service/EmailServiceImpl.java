package com.example.auctioneer.Service;

import com.example.auctioneer.Model.Auction;
import com.example.auctioneer.Model.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendBidEmail(Bid bid) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("doingjavastuff@gmail.com");
        simpleMailMessage.setTo(bid.getBidOwner().getUsername());
        simpleMailMessage.setSubject("Bid on " + bid.getAuction().getItem().getName()+ " successful.");
        simpleMailMessage.setText("Thank you for your bid! " +
                "\n Item: "+bid.getAuction().getItem().getName() +
                "\n Bid: "+bid.getBidPrice()+
                "\n By: "+ bid.getBidOwner().getName() +
                "\n Expires: "+bid.getAuction().getEndsAt());
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendAuctionEmail(Auction auction) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("doingjavastuff@gmail.com");
        simpleMailMessage.setTo(auction.getAuctionOwner().getUsername());
        simpleMailMessage.setSubject("Auction " + auction.getItem().getName()+ " successfully created.");
        simpleMailMessage.setText("Thank you for using our service! " +
                "\n Item: "+auction.getItem().getName() +
                "\n Starting Price: "+auction.getStartingPrice()+
                "\n By: "+ auction.getAuctionOwner().getName() +
                "\n Expires: "+auction.getEndsAt());
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendWinnersEmailBidder(Auction auction) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("doingjavastuff@gmail.com");
        simpleMailMessage.setTo(auction.getHighestBid().getBidOwner().getUsername());
        simpleMailMessage.setSubject("Congratulations, you've won " + auction.getItem().getName()+ "!");
        simpleMailMessage.setText("Details: " +
                "\n Item: "+auction.getItem().getName() +
                "\n Amount to pay: "+auction.getHighestBid().getBidPrice()+
                "\n Send money to: "+ auction.getAuctionOwner().getName() +", "+ auction.getAuctionOwner().getAddress()+", "+auction.getAuctionOwner().getPostNr()+
                "\n If you have questions regarding the item, please contact the seller on: "+auction.getAuctionOwner().getPhoneNr()+"or "+auction.getAuctionOwner().getUsername());
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendWinnersEmailAuctioneer(Auction auction) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("doingjavastuff@gmail.com");
        simpleMailMessage.setTo(auction.getAuctionOwner().getUsername());
        simpleMailMessage.setSubject("Congratulations, " + auction.getItem().getName()+ " has been sold!");
        simpleMailMessage.setText("Details: " +
                "\n Item: "+auction.getItem().getName() +
                "\n Send item to: "+auction.getHighestBid().getBidOwner().getAddress()+", "+auction.getHighestBid().getBidOwner().getPostNr()+
                "\n If you have questions regarding the item, please contact the buyer on: "+auction.getHighestBid().getBidOwner().getPhoneNr()+"or "+auction.getAuctionOwner().getUsername());
        javaMailSender.send(simpleMailMessage);
    }
}
