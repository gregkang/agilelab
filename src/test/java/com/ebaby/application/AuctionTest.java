package com.ebaby.application;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuctionTest {
    private Auction auction;
    private User userSeller;
    private User userBidder;
    private String itemDesc;
    private Double price;
    private Date startTime;
    private Date endTime;

    @Before
    public void setUp(){
        userSeller = new User("firsName","lastName", "userEmail", "userName", "password");
        userSeller.setRole(User.Role.SELLER);
        auction = new Auction(userSeller, itemDesc, price, startTime, endTime);
        userBidder = new User("firsName1","lastName1", "userEmail1", "userName1", "password1");
    }

    @Test
    public void checkAuctionConstruction(){

        Assert.assertEquals(auction.getUser(), userSeller);
        Assert.assertEquals(auction.getItemDesc(), itemDesc);
        Assert.assertEquals(auction.getPrice(), price);
        Assert.assertEquals(auction.getStartTime(), startTime);
        Assert.assertEquals(auction.getEndTime(), endTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAuctionWithBidder(){
        new Auction(userBidder, itemDesc, price, startTime, endTime);
    }
}
