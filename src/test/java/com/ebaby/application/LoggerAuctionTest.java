package com.ebaby.application;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.ebaby.services.AuctionLogger;
import com.ebaby.services.Hours;
import com.ebaby.services.OffHours;

public class LoggerAuctionTest {
    private static final String OFF_HOUR_LOGGER_FILE_NAME = "OffHourLogger.txt";
    private static final String HIGH_SALES_LOGGER_FILE_NAME = "SalesOver10K.txt";
    private AuctionLogger auctionLogger = AuctionLogger.getInstance();
    private Auction auction;
    private User userSeller;
    private User userBidder;
    private String itemDesc = "item description";
    private Double price = 3.0;
    private DateTime startTime;
    private DateTime endTime;
    private Category categoryCar = Category.CAR;

    private class ShuntOffHours implements Hours {
        private boolean offHours;

        private ShuntOffHours(boolean offHours) {
            this.offHours = offHours;

        }

        @Override
        public boolean isOffHours() {
            return offHours;
        }
    }

    @Before
    public void setup() {
        auctionLogger.clearLog(OFF_HOUR_LOGGER_FILE_NAME);
        auctionLogger.clearLog(HIGH_SALES_LOGGER_FILE_NAME);
        userSeller = new User("firsName", "lastName", "userEmail", "userName", "password");
        userSeller.setRole(User.Role.SELLER);
        userSeller.setAuthenticated(true);
        userBidder = new User("firsName1", "lastName1", "userEmail1", "userName1", "password1");
        userBidder.setAuthenticated(true);
        startTime = DateTime.now().plusDays(5);
        endTime = DateTime.now().plusDays(10);
        auction = new Auction(userSeller, itemDesc, price, startTime, endTime, categoryCar, OffHours.getInstance());
        auction.setActive(true);
    }

    @Test
    public void logAfterHoursFindMessage() {
        ShuntOffHours shuntOffHours = new ShuntOffHours(true);
        auction = new Auction(userSeller, itemDesc, price, startTime, endTime, categoryCar, shuntOffHours);
        auction.onClose();
        String expected = String.format("Item \"%s\" sold to buyer for \"%s\"",
                auction.getItemDesc(),
                auction.getBuyerAmount());
        assertTrue(auctionLogger.findMessage(OFF_HOUR_LOGGER_FILE_NAME, expected));
    }

    @Test
    public void logBeforeHoursFindMessage() {
        ShuntOffHours shuntOffHours = new ShuntOffHours(false);
        auction = new Auction(userSeller, itemDesc, price, startTime, endTime, categoryCar, shuntOffHours);
        auction.onClose();
        String expected = String.format("Item \"%s\" sold to buyer for \"%s\"",
                auction.getItemDesc(),
                auction.getBuyerAmount());
        assertFalse(auctionLogger.findMessage(OFF_HOUR_LOGGER_FILE_NAME, expected));
    }

    @Test
    public void checkLoggerForOver10000() {
        Double validPrice = 11000.0;
        auction.setCategory(Category.CAR);
        auction.bid(userBidder, validPrice);
        auction.onClose();
        String expected = String.format("Item \"%s\" sold to buyer for \"%s\"",
                auction.getItemDesc(),
                auction.getBuyerAmount());
        assertTrue(auctionLogger.findMessage(HIGH_SALES_LOGGER_FILE_NAME, expected));
    }

    @Test
    public void checkLoggerForNeither() {
        Double validPrice = 9000.0;
        auction.setCategory(Category.TOY);
        auction.bid(userBidder, validPrice);
        auction.onClose();
        String expected = String.format("Item \"%s\" sold to buyer for \"%s\"",
                auction.getItemDesc(),
                auction.getBuyerAmount());
        assertFalse(auctionLogger.findMessage(HIGH_SALES_LOGGER_FILE_NAME, expected));
    }
}
