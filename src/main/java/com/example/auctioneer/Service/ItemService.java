package com.example.auctioneer.Service;

import com.example.auctioneer.Model.Item;

public interface ItemService{
    void saveItem(Item item);
    void deleteItem(Long id);
}
