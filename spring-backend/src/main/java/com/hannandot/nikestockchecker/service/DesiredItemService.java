package com.hannandot.nikestockchecker.service;

import com.hannandot.nikestockchecker.exception.ItemNotFoundException;
import com.hannandot.nikestockchecker.model.DesiredItem;
import com.hannandot.nikestockchecker.repo.DesiredItemRepo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class that implements methods for CRUD operations to the database
 */
@Service
public class DesiredItemService {
    private final DesiredItemRepo desiredItemRepo;

    @Autowired
    public DesiredItemService(DesiredItemRepo desiredItemRepo) {
        this.desiredItemRepo = desiredItemRepo;
    }

    public DesiredItem addDesiredItem(DesiredItem desiredItem) {
        return desiredItemRepo.save(desiredItem);
    }

    public List<DesiredItem> findAllDesiredItems() {
        return desiredItemRepo.findAll();
    }

    public DesiredItem findDesiredItemById(Long id) {
        return desiredItemRepo.findDesiredItemByItemID(id).orElseThrow(() -> new ItemNotFoundException("No item with ID found."));
    }

    @Transactional
    public DesiredItem updateDesiredItem (DesiredItem desiredItem) {
        return desiredItemRepo.save(desiredItem);
    }

    @Transactional
    public void deleteDesiredItem (Long id) {
        desiredItemRepo.deleteDesiredItemByItemID(id);
    }

  /**
   * Fetches the name of the item using the URL by parsing the site HTML
   * Currently specified for nike.com
   * @param webURL nike.com item URL
   * @param driver web driver (Chrome in this implementation)
   * @return Name string
   */
  public String getName (String webURL, WebDriver driver){
    driver.get(webURL);
    // check if need to accept cookies - if present, accept otherwise, ignore
      try {
        if (driver.findElement(By.id("hf_cookie_text_cookieAccept")) != null) {

          driver.findElement(By.id("hf_cookie_text_cookieAccept")).click();
        }
      }
      catch (org.openqa.selenium.ElementNotInteractableException ignored) {

      }

    return driver.findElements(By.cssSelector("#pdp_product_title")).get(0).getAttribute("textContent");
    }

  /**
   * Fetches the url of the item image by parsing the site HTML
   * Currently specified for nike.com
   * @param webURL nike.com item URL
   * @param driver web driver (Chrome in this implementation)
   * @return image URL string
   */
    public String getImageURL (String webURL, WebDriver driver){
        driver.get(webURL);
        try {
          if (driver.findElement(By.id("hf_cookie_text_cookieAccept")) != null) {

            driver.findElement(By.id("hf_cookie_text_cookieAccept")).click();
          }
        }
        catch (org.openqa.selenium.ElementNotInteractableException ignored) {

        }
        WebElement img = driver.findElements(By.cssSelector("#pdp-6-up > button:nth-child(2) > div > picture:nth-child(3) > img")).get(0);

        return img.getAttribute("src");
    }

  /**
   * Fetches stock status for selected size by parsing site HTML
   * Currently specified for nike.com
   * @param webURL nike.com item URL
   * @param shoeSize item size
   * @param driver web driver (Chrome in this implementation)
   * @return stock boolean
   */
        public boolean checkStock (String webURL, String shoeSize, WebDriver driver){

            driver.get(webURL);
          try {
            if (driver.findElement(By.id("hf_cookie_text_cookieAccept")) != null) {

              driver.findElement(By.id("hf_cookie_text_cookieAccept")).click();
            }
          }
          catch (org.openqa.selenium.ElementNotInteractableException ignored) {

          }

            List<WebElement> sizes = driver.findElements(By.xpath("//*[@id=\"buyTools\"]/div[1]/fieldset/div/div"));

            shoeSize = "UK " + shoeSize;

            // TODO: remove EU size from retrieved size text
            for (WebElement div : sizes) {

                String sizeString = div.getText();

                if (sizeString.equals(shoeSize)) {
                    String stock = div.findElement(By.name("skuAndSize")).getAttribute("disabled");
                    return stock == null;
                }
            }
            // TODO: throw exception if size not found
            return false;
        }

    }
