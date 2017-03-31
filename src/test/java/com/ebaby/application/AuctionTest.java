package com.ebaby.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.ebaby.services.AuctionLogger;
import com.ebaby.services.OffHours;
import com.ebaby.services.PostOffice;

public class AuctionTest {
    private Auction auction;
    private User userSeller;
    private User userBidder;
    private String itemDesc = "item description";
    private Double price = 3.0;
    private DateTime startTime;
    private DateTime endTime;
    private Category categoryCar = Category.CAR;
    private PostOffice postOffice;
    private AuctionLogger auctionLogger;

    @Before
    public void setUp() {
        userSeller = new User("firsName", "lastName", "userEmail", "userName", "password");
        userSeller.setRole(User.Role.SELLER);
        userSeller.setAuthenticated(true);
        userBidder = new User("firsName1", "lastName1", "userEmail1", "userName1", "password1");
        userBidder.setAuthenticated(true);
        startTime = DateTime.now().plusDays(5);
        endTime = DateTime.now().plusDays(10);
        auction = new Auction(userSeller, itemDesc, price, startTime, endTime, categoryCar, OffHours.getInstance());
        postOffice = PostOffice.getInstance();
        auctionLogger = AuctionLogger.getInstance();
        auction.setActive(true);
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
        new Auction(userBidder, itemDesc, price, startTime, endTime, categoryCar, OffHours.getInstance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAuctionWithStartTimeBeforeNow() {
        DateTime invalidStartTime = DateTime.now().minusDays(10);
        new Auction(userSeller, itemDesc, price, invalidStartTime, endTime, categoryCar, OffHours.getInstance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAuctionWithStartTimeAfterEndTime() {
        DateTime invalidEndTime = DateTime.now().plusDays(2);
        new Auction(userSeller, itemDesc, price, startTime, invalidEndTime, categoryCar, OffHours.getInstance());
    }

    @Test
    public void checkBidSuccess() {
        Double validPrice = 4.0;
        auction.bid(userBidder, validPrice);
        assertEquals(userBidder, auction.getHighestBidder());
        assertEquals(validPrice, auction.getCurrentHighBid());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkBidOffHours() {
        Double validPrice = 4.0;
        auction.setActive(false);
        auction.bid(userBidder, validPrice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAuctionWithInvalidPrice() {
        Double invalidPrice = 2.0;
        auction.bid(userBidder, invalidPrice);
    }

    @Test
    public void checkOnCloseWithSale() {
        Double validPrice = 4.0;
        auction.bid(userBidder, validPrice);
        auction.onClose();
        assertTrue(postOffice.doesLogContain(userSeller.getUserEmail(),
                String.format(" Your \"%s\" auction sold to bidder \"%s\" for \"%s\"",
                        auction.getItemDesc(),
                        auction.getHighestBidder().getUserEmail(),
                        auction.getCurrentHighBid())));
        assertTrue(postOffice.doesLogContain(userBidder.getUserEmail(),
                String.format("Congratulations! you won an auction for a \"%s\" from \"%s\" for \"%s\"",
                        auction.getItemDesc(),
                        auction.getUser().getUserEmail(),
                        auction.getCurrentHighBid())));
    }

    @Test
    public void checkOnCloseWithNoSale() {
        auction.onClose();
        assertTrue(postOffice.doesLogContain(userSeller.getUserEmail(),
                String.format("Sorry, your auction for \"%s\" did not have any bidders", auction.getItemDesc())));
    }

    @Test
    public void checkFeesforDownloadbleSoftware() {
        Double validPrice = 4.0;
        auction.setCategory(Category.DOWNLOADABLE_SOFTWARE);
        auction.bid(userBidder, validPrice);
        auction.onClose();
        assertEquals(validPrice, auction.getBuyerAmount());
    }

    @Test
    public void checkFeesforCarUnder50K() {
        Double validPrice = 4.0;
        auction.setCategory(Category.CAR);
        auction.bid(userBidder, validPrice);
        auction.onClose();
        Double finalPrice = validPrice + 1000;
        assertEquals(finalPrice, auction.getBuyerAmount());
    }

    @Test
    public void checkFeesforCarAbove50K() {
        Double validPrice = 51000.0;
        auction.setCategory(Category.CAR);
        auction.bid(userBidder, validPrice);
        auction.onClose();
        Double luxuryTax = (validPrice * 4) / 100;
        Double finalPrice = validPrice + luxuryTax + 1000;
        assertEquals(finalPrice, auction.getBuyerAmount());
    }

    @Test
    public void checkTransactionFeeOnSeller() {
        Double validPrice = 1000.0;
        auction.bid(userBidder, validPrice);
        auction.onClose();
        Double transactionFees = (validPrice * 2) / 100;
        Double finalPrice = validPrice - transactionFees;
        assertEquals(finalPrice, auction.getSellerAmount());
    }

    @Test
    public void checkShippingFeeOnOtherCategories() {
        Double validPrice = 1000.0;
        auction.setCategory(Category.TOY);
        auction.bid(userBidder, validPrice);
        auction.onClose();
        Double finalPrice = validPrice + 10;
        assertEquals(finalPrice, auction.getBuyerAmount());
    }

    @Test
    public void checkLoggerForCar() {
        Double validPrice = 1000.0;
        auction.setCategory(Category.CAR);
        auction.bid(userBidder, validPrice);
        auction.onClose();
        String expected = String.format("Item \"%s\" sold to buyer for \"%s\"",
                auction.getItemDesc(),
                auction.getBuyerAmount());
        assertTrue(auctionLogger.findMessage("CarSales.txt", expected));
    }
}
