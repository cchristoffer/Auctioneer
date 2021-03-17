package com.example.auctioneer.Model;

import javax.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String name;
    private String description;
    private String img;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_categoryId", referencedColumnName = "categoryId")
    private Category category;

    public Item() {
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }



    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
