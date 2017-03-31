package com.ebaby.services;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.ebaby.application.Auction;
import com.ebaby.application.AuctionManager;
import com.ebaby.application.Category;
import com.ebaby.application.User;

public class AuctionTimerTest {
    private class ShuntTimer extends AuctionTimer {
        @Override
        public void start() {
        }
    }

    private Auction auction;
    private User userSeller;
    private User userBidder;
    private String itemDesc = "item description";
    private Double price = 3.0;
    private DateTime startTime;
    private DateTime endTime;
    private Category categoryCar = Category.CAR;
    private AuctionManager auctionManager;

    @Before
    public void setUp() {
        userSeller = new User("firsName", "lastName", "userEmail", "userName", "password");
        userSeller.setRole(User.Role.SELLER);
        userBidder = new User("firsName1", "lastName1", "userEmail1", "userName1", "password1");
        startTime = DateTime.now().plusDays(1);
        endTime = DateTime.now().plusDays(10);
        auctionManager = new AuctionManager();
        auctionManager.getAuctions().add(auction);
        auctionManager.getUsers().register(userSeller);
        auctionManager.getUsers().register(userBidder);
        auctionManager.getUsers().login("userName", "password");
        auctionManager.getUsers().login("userName1", "password1");
        auction = new Auction(userSeller, itemDesc, price, startTime, endTime, categoryCar, OffHours.getInstance());

    }

    @Test
    public void test() {
        ShuntTimer timer = new ShuntTimer();
        timer.checkAuction(auctionManager);
        timer.start();
        // not yet implemented
    }
}
