package com.ebaby.application;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.ebaby.services.OffHours;

public class PreferredSellerAuctionTest {
    private Auction auction;
    private User userSeller;
    private User userBidder;
    private String itemDesc = "item description";
    private Double price = 3.0;
    private DateTime startTime;
    private DateTime endTime;
    private Category categoryCar = Category.CAR;

    @Before
    public void setUp() {
        userSeller = new User("firsName", "lastName", "userEmail", "userName", "password");
        userSeller.setRole(User.Role.PREFERRED_SELLER);
        userSeller.setAuthenticated(true);
        userBidder = new User("firsName1", "lastName1", "userEmail1", "userName1", "password1");
        userBidder.setAuthenticated(true);
        startTime = DateTime.now().plusDays(5);
        endTime = DateTime.now().plusDays(10);
        auction = new Auction(userSeller, itemDesc, price, startTime, endTime, categoryCar, OffHours.getInstance());
        auction.setActive(true);
    }

    @Test
    public void testFreeShippingForNonCarOver50() {
        Double validPrice = 60.0;
        auction.setCategory(Category.TOY);
        auction.bid(userBidder, validPrice);
        auction.onClose();
        assertEquals(validPrice, auction.getBuyerAmount());
        assertEquals(auction.getCurrentHighBid() * 0.99, auction.getSellerAmount(), 0);
    }

    @Test
    public void testHalfPriceShippingForAllCars() {
        Double validPrice = 50.0;
        auction.setCategory(Category.CAR);
        auction.bid(userBidder, validPrice);
        auction.onClose();
        Double finalPrice = validPrice + 500;
        assertEquals(finalPrice, auction.getBuyerAmount());
        assertEquals(auction.getCurrentHighBid() * 0.99, auction.getSellerAmount(), 0);
    }

}
