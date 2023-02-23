package com.hannandot.nikestockchecker.model;





import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Entity class for a general item
 */
@Entity
public class DesiredItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long itemID;
    private String itemURL;
    private String name;
    private String size;
    private Boolean isInStock;
    private String imageURL;

    public DesiredItem(){}

    public DesiredItem(Long itemID, String itemURL, String name, String size, Boolean isInStock, String imageURL) {
        this.itemID = itemID;
        this.itemURL = itemURL;
        this.name = name;
        this.size = size;
        this.isInStock = isInStock;
        this.imageURL = imageURL;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    public String getItemURL() {
        return itemURL;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Boolean getInStock() {
        return isInStock;
    }

    public void setInStock(Boolean inStock) {
        isInStock = inStock;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}


