package com.example.auctioneer.Factory;

import com.example.auctioneer.Model.Auction;
import com.example.auctioneer.Service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CategoryFactoryImpl implements CategoryFactory{

    @Autowired
    AuctionService auctionService;

    @Override
    public Page<Auction> getAllByCategory(String category, PageRequest page) {
        Long id = (long)1;
        if(category.equals("vehicles")){
            id = (long)1;
        }
        if(category.equals("music")){
            id = (long)2;
        }
        if(category.equals("games")){
            id = (long)3;
        }
        if(category.equals("electronics")){
            id = (long)4;
        }
        if(category.equals("animals")){
            id = (long)5;
        }
        if(category.equals("clothes")){
            id = (long)6;
        }
        return auctionService.findAllByCategoryIdDateAsc(id, page);
    }

    @Override
    public String getTitle(String category) {
        String title = "Unknown category";
        if(category.equals("vehicles")){
            title = "Vehicles";
        }
        if(category.equals("music")){
            title = "Music Instruments";
        }
        if(category.equals("games")){
            title = "Games & Consoles";
        }
        if(category.equals("electronics")){
            title = "Electronics";
        }
        if(category.equals("animals")){
            title = "Animals";
        }
        if(category.equals("clothes")){
            title = "Clothes & Accessories";
        }
        return title;
    }
}
