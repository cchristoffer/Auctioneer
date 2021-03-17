package com.example.auctioneer.Controller;

import com.example.auctioneer.Factory.CategoryFactory;
import com.example.auctioneer.Model.Auction;
import com.example.auctioneer.Model.Bid;
import com.example.auctioneer.Model.Item;
import com.example.auctioneer.Observable.AuctionObserver;
import com.example.auctioneer.Observable.AuctionPublisher;
import com.example.auctioneer.Observable.BidObserver;
import com.example.auctioneer.Repository.BidRepository;
import com.example.auctioneer.Service.AuctionService;
import com.example.auctioneer.Service.CategoryService;
import com.example.auctioneer.Service.ItemService;
import com.example.auctioneer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("auctioneer")
public class AuctionController {

    @Autowired
    AuctionService auctionService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Autowired
    CategoryFactory categoryFactory;

    @Autowired
    AuctionPublisher auctionPublisher;

    @Autowired
    BidObserver bidObserver;

    @Autowired
    AuctionObserver auctionObserver;

    @Autowired
    BidRepository bidRepository;


    @GetMapping("/{pageno}")
    public String welcome(@PathVariable() Integer pageno, HttpServletRequest request, Principal principal, Model model){
        if (request.isUserInRole("ROLE_ADMIN")){
            return "redirect:/admin/auctions/0";
        }

        if(pageno <= 0){
            pageno = 0;
        }
        final int PAGESIZE = 4;
        PageRequest paging = PageRequest.of(pageno, PAGESIZE);
        Page<Auction> pagedResult = auctionService.findAllByDateAsc(paging);
        List<Auction>  homeList;
        homeList = pagedResult.getContent();

        model.addAttribute("currentPageNumber", pagedResult.getNumber());
        model.addAttribute("displayableCurrentPageNumber", pagedResult.getNumber()+1);
        model.addAttribute("previousPageNumber", pageno-1);
        model.addAttribute("nextPageNumber", pageno+1);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());
        model.addAttribute("auctions", homeList);
        model.addAttribute("title", "Ending soon");
        model.addAttribute("name", principal.getName());
        return "home";
    }

    @PostMapping("/search/{pageno}")
    public String search(@PathVariable() Integer pageno, @RequestParam String search, Principal principal, Model model){
        if(pageno <= 0){
            pageno = 0;
        }
        final int PAGESIZE = 4;
        PageRequest paging = PageRequest.of(pageno, PAGESIZE);
        Page<Auction> pagedResult = auctionService.searchAuctions(search, paging);
        List<Auction>  homeList;
        homeList = pagedResult.getContent();

        model.addAttribute("currentPageNumber", pagedResult.getNumber());
        model.addAttribute("displayableCurrentPageNumber", pagedResult.getNumber()+1);
        model.addAttribute("previousPageNumber", pageno-1);
        model.addAttribute("nextPageNumber", pageno+1);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());
        model.addAttribute("auctions", homeList);
        model.addAttribute("title", "Results for search: " + "\""+search+"\"");
        model.addAttribute("name", principal.getName());
        return "home";
    }

    @GetMapping("/{category}/{pageno}")
    public String category(@PathVariable String category,@PathVariable() Integer pageno, HttpServletRequest request, Principal principal, Model model){

        if(pageno <= 0){
            pageno = 0;
        }
        final int PAGESIZE = 4;
        PageRequest paging = PageRequest.of(pageno, PAGESIZE);
        Page<Auction> pagedResult = categoryFactory.getAllByCategory(category, paging);
        List<Auction> homeList;
        homeList = pagedResult.getContent();

        model.addAttribute("currentPageNumber", pagedResult.getNumber());
        model.addAttribute("displayableCurrentPageNumber", pagedResult.getNumber()+1);
        model.addAttribute("previousPageNumber", pageno-1);
        model.addAttribute("nextPageNumber", pageno+1);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());
        model.addAttribute("auctions", homeList);
        model.addAttribute("title", categoryFactory.getTitle(category));
        model.addAttribute("name", principal.getName());
        return "home";
    }

    @GetMapping("/item/{id}")
    public String auctionItem(@PathVariable Long id, Model model){
        Auction auction = auctionService.findAuctionById(id);
        boolean hasBid = false;
        Double highestBid = auction.getStartingPrice();
        if(auction.getHighestBid()!=null){
            highestBid = auction.getHighestBid().getBidPrice();
            hasBid = true;
        }
        List<Bid> bidsList = bidRepository.findAllBidsByPrice(id);
        model.addAttribute("title", auction.getItem().getName());
        model.addAttribute("auction", auction);
        model.addAttribute("highestBid", highestBid);
        model.addAttribute("numBids", auction.getBids().size());
        model.addAttribute("hasBid", hasBid);
        model.addAttribute("bids", bidsList);

        return "auctionItem";
    }

    @PostMapping("/bid")
    public String bidItem(@RequestParam Long id, @RequestParam double bid, Principal principal, Model model){
        Auction auction = auctionService.findAuctionById(id);
        boolean hasBid = false;
        Double highestBid = auction.getStartingPrice();
        List<Bid> bidsList = bidRepository.findAllBidsByPrice(id);
        if(auction.getHighestBid()!=null) {
            highestBid = auction.getHighestBid().getBidPrice();
            hasBid = true;
        }
        if(auctionService.findAuctionById(id).getHighestBid() != null){
            if(bid <= auctionService.findAuctionById(id).getHighestBid().getBidPrice()){
                model.addAttribute("title", auction.getItem().getName());
                model.addAttribute("auction", auction);
                model.addAttribute("highestBid", highestBid);
                model.addAttribute("numBids", auction.getBids().size());
                model.addAttribute("hasBid", hasBid);
                model.addAttribute("bids", bidsList);
                model.addAttribute("msg", "Unsuccessful, please enter a bid above currently highest bid.");
                model.addAttribute("class", "text-danger");
                return "auctionItem";
            }
        }
        Bid newBid = new Bid();
        newBid.setBidOwner(userService.findByName(principal.getName()));
        newBid.setBidPrice(bid);
        newBid.setAuction(auction);
        auction.getBids().add(newBid);
        auctionService.saveAuction(auction);
        auctionPublisher.addObserver(bidObserver);
        auctionPublisher.publishNewBid(newBid);

        model.addAttribute("highestBid", highestBid);
        model.addAttribute("numBids", auction.getBids().size());
        model.addAttribute("hasBid", hasBid);
        model.addAttribute("title", auction.getItem().getName());
        model.addAttribute("auction", auction);
        model.addAttribute("bids", bidsList);
        model.addAttribute("msg", "Successfully placed bid of "+bid+" on item..");
        model.addAttribute("class", "text-success");
        return "auctionItem";
    }

    @GetMapping("/create")
    public String createView(Model model, Principal principal){
        return "createAuction";
    }

    @PostMapping("/create")
    public String createAuction(Principal principal, String name, String img, String description, String endsAt, Double startingPrice, Long category){
        Item item = new Item();
        item.setCategory(categoryService.getCategoryById(category));
        item.setDescription(description);
        item.setName(name);
        item.setImg(img);
        itemService.saveItem(item);

        Auction auction = new Auction();
        auction.setAuctionOwner(userService.findByName(principal.getName()));
        System.out.println(userService.findByName(principal.getName()).getUsername());
        auction.setItem(item);
        auction.setStartingPrice(startingPrice);
        auction.setActive(true);

        long millis=System.currentTimeMillis();
        if(endsAt.equals("short")){
            java.sql.Date date = new java.sql.Date(millis + (1000 * 60 * 60 * 24));
            auction.setEndsAt(date);
        }
        if(endsAt.equals("medium")){
            java.sql.Date date = new java.sql.Date(millis + (1000 * 60 * 60 * 72));
            auction.setEndsAt(date);
        }
        if(endsAt.equals("long")){
            java.sql.Date date = new java.sql.Date(millis + (1000 * 60 * 60 * 168));
            auction.setEndsAt(date);
        }
        java.sql.Date startDate = new java.sql.Date(millis);
        auction.setStartedAt(startDate);
        auctionService.saveAuction(auction);
        auctionPublisher.addObserver(auctionObserver);
        auctionPublisher.publishNewAuction(auction);

        return "redirect:/auctioneer/0";
    }

    @GetMapping("/mybids")
    public String myBids(Model model, Principal principal){

        model.addAttribute("bids", userService.findByName(principal.getName()).getBids());
        model.addAttribute("title", "My Bids");
        return "myData";
    }

    @GetMapping("/myauctions")
    public String myAuctions(Model model, Principal principal){

        model.addAttribute("auctions", userService.findByName(principal.getName()).getAuctions());
        model.addAttribute("title", "My Auctions");
        return "myData";
    }

    @PostMapping("/update")
    public String updateAuction(Model model, Principal principal, @RequestParam String name, @RequestParam String img, @RequestParam String description, @RequestParam Long id, @RequestParam Long category){
        Auction auction = auctionService.findAuctionById(id);
        auction.getItem().setImg(img);
        auction.getItem().setName(name);
        auction.getItem().setDescription(description);
        auction.getItem().setCategory(categoryService.getCategoryById(category));
        auctionService.saveAuction(auction);

        return "redirect:/auctioneer/myauctions";
    }

    @PostMapping("/delete")
    public String deleteAuction(Model model, @RequestParam Long id){
        itemService.deleteItem(auctionService.findAuctionById(id).getItem().getItemId());
        auctionService.deleteAuction(id);
        return "redirect:/auctioneer/myauctions";
    }
}
