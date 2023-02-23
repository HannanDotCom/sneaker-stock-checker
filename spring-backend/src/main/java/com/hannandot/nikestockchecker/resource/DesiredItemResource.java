package com.hannandot.nikestockchecker.resource;

import com.hannandot.nikestockchecker.model.DesiredItem;
import com.hannandot.nikestockchecker.service.DesiredItemService;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages Http requests and responses from the front-end for CRUD operations to the item database
 */
@Controller
@RequestMapping("/items")
public class DesiredItemResource {
    private final DesiredItemService desiredItemService;

    public DesiredItemResource(DesiredItemService desiredItemService) {
        this.desiredItemService = desiredItemService;
    }

  /**
   * Function that obtains list of all items in database
   * @return ResponseEntity of item list
   */
  @GetMapping("/all")
    public ResponseEntity<List<DesiredItem>> getAllDesiredItems() {
        List<DesiredItem> desiredItemList = desiredItemService.findAllDesiredItems();
        return new ResponseEntity<>(desiredItemList, HttpStatus.OK);
    }

  /**
   * Function that fetches item by its unique id from a database
   * @param id Long
   * @return Item in Http response
   */
  @GetMapping("/find/{id}")
    public ResponseEntity<DesiredItem> getDesiredItemById(@PathVariable("id") Long id) {
        DesiredItem desiredItem = desiredItemService.findDesiredItemById(id);
        return new ResponseEntity<>(desiredItem, HttpStatus.OK);
    }

  /**
   * Function that adds new item to database whilst fetching the name, image URL and stock status of the item
   * @param desiredItem item
   * @return Item in Http response
   * @throws IOException
   */
  @PostMapping("/add")
    public ResponseEntity<DesiredItem> addDesiredItem(@RequestBody DesiredItem desiredItem) throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu","--ignore-certificate-errors","--disable-extensions","--no-sandbox","--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);
        // Name, image URL and stock status retrieved from using service class
        desiredItem.setName(desiredItemService.getName(desiredItem.getItemURL(), driver));
        desiredItem.setImageURL(desiredItemService.getImageURL(desiredItem.getItemURL(), driver));
        desiredItem.setInStock(desiredItemService.checkStock(desiredItem.getItemURL(), desiredItem.getSize(), driver));
        driver.quit();
        DesiredItem newDesiredItem = desiredItemService.addDesiredItem(desiredItem);
        return new ResponseEntity<>(newDesiredItem,HttpStatus.CREATED);
    }

  /**
   * Function that updates the info of an item in the database
   * @param desiredItem item
   * @return updated item in Http response
   */
  @PutMapping("/update")
    public ResponseEntity<DesiredItem> updateDesiredItem(@RequestBody DesiredItem desiredItem) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu","--ignore-certificate-errors","--disable-extensions","--no-sandbox","--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);
        // Name, image URL and stock status updated upon PUT request
        desiredItem.setName(desiredItemService.getName(desiredItem.getItemURL(), driver));
        desiredItem.setImageURL(desiredItemService.getImageURL(desiredItem.getItemURL(), driver));
        desiredItem.setInStock(desiredItemService.checkStock(desiredItem.getItemURL(), desiredItem.getSize(), driver));
        driver.quit();
        DesiredItem updateDesiredItem = desiredItemService.updateDesiredItem(desiredItem);
        return new ResponseEntity<>(updateDesiredItem, HttpStatus.OK);
    }

  /**
   * Function that updates the stock of all items in the database
   * @param desiredItemList list of all items
   * @return the list of all items in the database with updated stock
   */
  @PutMapping("/updatestock")
    public ResponseEntity<List<DesiredItem>> updateDesiredItemStock (@RequestBody List<DesiredItem> desiredItemList) {
        List<DesiredItem> updatedList = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu","--ignore-certificate-errors","--disable-extensions","--no-sandbox","--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);

        if (desiredItemList.size() != 0) {
            for (DesiredItem desiredItem : desiredItemList) {
                desiredItem.setInStock(desiredItemService.checkStock(desiredItem.getItemURL(), desiredItem.getSize(), driver));
                DesiredItem updateDesiredItem = desiredItemService.updateDesiredItem(desiredItem);
                updatedList.add(updateDesiredItem);
            }

        }

        driver.quit();
        return new ResponseEntity<>(updatedList, HttpStatus.OK);
    }

  /**
   * Function that deletes item in the database by id
   * @param id item id
   * @return Empty response entity
   */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDesiredItem(@PathVariable("id") Long id) {
        desiredItemService.deleteDesiredItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
