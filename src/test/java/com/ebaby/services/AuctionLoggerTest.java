package com.ebaby.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AuctionLoggerTest {
    private AuctionLogger auctionLogger = AuctionLogger.getInstance();
    private String filename1 = "filename1.txt";
    private String message1 = "a message1";
    private String filename2 = "filename2.txt";
    private String message2 = "a message2";
    private String message3 = "a message3";

    @Before
    public void setup() {
        auctionLogger.clearLog(filename1);
        auctionLogger.clearLog(filename2);
    }

    @Test
    public void logAndFindMessage() {
        auctionLogger.log(filename1, message1);
        assertTrue(auctionLogger.findMessage(filename1, message1));
    }

    @Test
    public void logAndFindMessageInvalidFilename() {
        auctionLogger.log(filename1, message1);
        assertFalse(auctionLogger.findMessage(filename2, message1));
    }

    @Test
    public void logAndFindMessageInvalidMessage() {
        auctionLogger.log(filename1, message1);
        assertFalse(auctionLogger.findMessage(filename1, message2));
    }

    @Test
    public void logAndReturnMessage() {
        auctionLogger.log(filename1, message1);
        auctionLogger.log(filename1, message2);
        String actualMessage = auctionLogger.returnMessage(filename1, message1);
        assertEquals(message1, actualMessage);
    }

    @Test
    public void logAndReturnMessageInvalidFilename() {
        auctionLogger.log(filename1, message1);
        String actualMessage = auctionLogger.returnMessage(filename2, message1);
        assertEquals("", actualMessage);
    }

    @Test
    public void logAndReturnMessageInvalidMessage() {
        auctionLogger.log(filename1, message1);
        String actualMessage = auctionLogger.returnMessage(filename1, message3);
        assertEquals("", actualMessage);
    }
}
