package com.ebaby.application;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuctionTest {
    private Auction auction;
    private User userSeller;
    private User userBidder;
    private String itemDesc = "item description";
    private Double price = 3.0;
    private DateTime startTime;
    private DateTime endTime;

    @Before
    public void setUp() {
        userSeller = new User("firsName", "lastName", "userEmail", "userName", "password");
        userSeller.setRole(User.Role.SELLER);
        userBidder = new User("firsName1", "lastName1", "userEmail1", "userName1", "password1");
        startTime = DateTime.now().plusDays(5);
        endTime = DateTime.now().plusDays(10);
        auction = new Auction(userSeller, itemDesc, price, startTime, endTime);
    }

    @Test
    public void checkAuctionConstruction() {
        Assert.assertEquals(auction.getUser(), userSeller);
        Assert.assertEquals(auction.getItemDesc(), itemDesc);
        Assert.assertEquals(auction.getPrice(), price);
        Assert.assertEquals(auction.getStartTime(), startTime);
        Assert.assertEquals(auction.getEndTime(), endTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAuctionWithBidder() {
        new Auction(userBidder, itemDesc, price, startTime, endTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAuctionWithStartTimeBeforeNow() {
        DateTime invalidStartTime = DateTime.now().minusDays(10);
        new Auction(userSeller, itemDesc, price, invalidStartTime, endTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAuctionWithStartTimeAfterEndTime() {
        DateTime invalidEndTime = DateTime.now().plusDays(2);
        new Auction(userSeller, itemDesc, price, startTime, invalidEndTime);
    }
}
