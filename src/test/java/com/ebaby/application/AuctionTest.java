package com.ebaby.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ebaby.services.PostOffice;
import org.joda.time.DateTime;
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
    private boolean auctionActive;
    private Auction.Categories categoryCar = Auction.Categories.Car;
    private PostOffice postOffice;

    @Before
    public void setUp() {
        userSeller = new User("firsName", "lastName", "userEmail", "userName", "password");
        userSeller.setRole(User.Role.SELLER);
        userSeller.setAuthenticated(true);
        userBidder = new User("firsName1", "lastName1", "userEmail1", "userName1", "password1");
        userBidder.setAuthenticated(true);
        startTime = DateTime.now().plusDays(5);
        endTime = DateTime.now().plusDays(10);
        auction = new Auction(userSeller, itemDesc, price, startTime, endTime, categoryCar);
        postOffice = PostOffice.getInstance();
    }

    @Test
    public void checkAuctionConstruction() {
        assertEquals(userSeller, auction.getUser());
        assertEquals(itemDesc, auction.getItemDesc());
        assertEquals(price, auction.getPrice());
        assertEquals(startTime, auction.getStartTime());
        assertEquals(endTime, auction.getEndTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAuctionWithBidder() {
        new Auction(userBidder, itemDesc, price, startTime, endTime,categoryCar);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAuctionWithStartTimeBeforeNow() {
        DateTime invalidStartTime = DateTime.now().minusDays(10);
        new Auction(userSeller, itemDesc, price, invalidStartTime, endTime,categoryCar);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAuctionWithStartTimeAfterEndTime() {
        DateTime invalidEndTime = DateTime.now().plusDays(2);
        new Auction(userSeller, itemDesc, price, startTime, invalidEndTime,categoryCar);
    }

    @Test
    public void checkBidSuccess() {
        DateTime validTime = DateTime.now().plusDays(7);
        Double validPrice = 4.0;
        auction.bid(userBidder, validPrice, validTime);
        assertEquals(userBidder, auction.getHighestBidder());
        assertEquals(validPrice, auction.getCurrentHighBid());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAuctionWithInvalidPrice() {
        DateTime validTime = DateTime.now().plusDays(7);
        Double invalidPrice = 2.0;
        auction.bid(userBidder, invalidPrice, validTime);
    }

    @Test
    public void checkOnCloseWithSale(){
        DateTime validTime = DateTime.now().plusDays(7);
        Double validPrice = 4.0;
        auction.bid(userBidder, validPrice, validTime);
        auction.onClose();
        assertTrue(postOffice.doesLogContain(userSeller.getUserEmail(), String.format(" Your \"%s\" auction sold to bidder \"%s\" for \"%s\"", auction.getItemDesc(), auction.getHighestBidder().getUserEmail(), auction.getCurrentHighBid())));
        assertTrue(postOffice.doesLogContain(userBidder.getUserEmail(), String.format("Congratulations! you won an auction for a \"%s\" from \"%s\" for \"%s\"", auction.getItemDesc(), auction.getUser().getUserEmail(), auction.getCurrentHighBid())));
    }

    @Test
    public void checkOnCloseWithNoSale(){
        auction.onClose();
        assertTrue(postOffice.doesLogContain(userSeller.getUserEmail(), String.format("Sorry, your auction for \"%s\" did not have any bidders", auction.getItemDesc())));
    }
}
