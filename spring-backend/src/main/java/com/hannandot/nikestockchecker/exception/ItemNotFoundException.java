package com.hannandot.nikestockchecker.exception;

/**
 * Class that handles the exception where an item that does not exist is accessed.
 */
public class ItemNotFoundException extends RuntimeException{
  /**
   * Throws exception when item is not found in database.
   * @param message Message to be shown upon error
   */
    public ItemNotFoundException(String message) {
        super(message);
    }
}
