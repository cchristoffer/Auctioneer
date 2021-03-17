package com.example.auctioneer.Controller;

import com.example.auctioneer.Model.Auction;
import com.example.auctioneer.Model.Users;
import com.example.auctioneer.Service.AuctionService;
import com.example.auctioneer.Service.CategoryService;
import com.example.auctioneer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    AuctionService auctionService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/auctions/{pageno}")
    public String auctions(@PathVariable() Integer pageno, Model model){
        if(pageno <= 0){
            pageno = 0;
        }
        final int PAGESIZE = 5;
        PageRequest paging = PageRequest.of(pageno, PAGESIZE);
        Page<Auction> pagedResult = auctionService.findAllOrderBydate(paging);
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
        model.addAttribute("title", "Auctions");
        return "admin";
    }

    @PostMapping("/auctions/update")
    public String updateAuction(@RequestParam String name, @RequestParam String img, @RequestParam String description, @RequestParam Long id, @RequestParam Long category){
        Auction auction = auctionService.findAuctionById(id);
        auction.getItem().setImg(img);
        auction.getItem().setName(name);
        auction.getItem().setDescription(description);
        auction.getItem().setCategory(categoryService.getCategoryById(category));
        auctionService.saveAuction(auction);

        return "redirect:/admin/auctions/0";
    }

    @PostMapping("/users/update")
    public String updateUser(@RequestParam String name, @RequestParam String username, @RequestParam String address, @RequestParam Long id, @RequestParam Integer enabled, @RequestParam String postNr, @RequestParam String phoneNr){
        Users user = userService.findById(id);
        user.setName(name);
        user.setUsername(username);
        user.setAddress(address);
        user.setPostNr(postNr);
        user.setPhoneNr(phoneNr);
        user.setEnabled(enabled);
        userService.save(user);
        return "redirect:/admin/auctions/0";
    }

    @PostMapping("/auctions/delete")
    public String deleteAuction(@RequestParam Long id){
        auctionService.deleteAuction(id);
        return "redirect:/admin/auctions/0";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam Long id){
        userService.deleteUser(id);
        return "redirect:/admin/auctions/0";
    }

    @GetMapping("/users/{pageno}")
    public String users(@PathVariable() Integer pageno, Model model){
        if(pageno <= 0){
            pageno = 0;
        }
        final int PAGESIZE = 5;
        PageRequest paging = PageRequest.of(pageno, PAGESIZE);
        Page<Users> pagedResult = userService.findAll(paging);
        List<Users> homeList;
        homeList = pagedResult.getContent();

        model.addAttribute("currentPageNumber", pagedResult.getNumber());
        model.addAttribute("displayableCurrentPageNumber", pagedResult.getNumber()+1);
        model.addAttribute("previousPageNumber", pageno-1);
        model.addAttribute("nextPageNumber", pageno+1);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());
        model.addAttribute("users", homeList);
        model.addAttribute("title", "Auctions");
        return "admin";
    }
}
