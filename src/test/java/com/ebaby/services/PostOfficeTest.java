package com.ebaby.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class PostOfficeTest {
    private PostOffice postOffice = PostOffice.getInstance();
    private String address1 = "address1@address.com";
    private String message1 = "a message1";
    private String address2 = "address2@address.com";
    private String message2 = "a message2";

    @Before
    public void setup() {
        postOffice.clear();
    }

    @Test
    public void testSendAndFindEMail() {
        postOffice.sendEMail(address1, message1);
        assertEquals(1, postOffice.size());
        String actualMessage = postOffice.findEmail(address1, "1");
        assertTrue(StringUtils.contains(actualMessage, address1));
        assertTrue(StringUtils.contains(actualMessage, message1));
    }

    @Test
    public void testFindEmailInvalidAddress() {
        postOffice.sendEMail(address1, message1);
        assertEquals(1, postOffice.size());
        String actualMessage = postOffice.findEmail(address2, "1");
        assertEquals("", actualMessage);
    }

    @Test
    public void testFindEmailInvalidMessage() {
        postOffice.sendEMail(address1, message1);
        assertEquals(1, postOffice.size());
        String actualMessage = postOffice.findEmail(address1, "2");
        assertEquals("", actualMessage);
    }

    @Test
    public void testDoesLogContain() {
        postOffice.sendEMail(address1, message1);
        assertTrue(postOffice.doesLogContain(address1, message1));
    }

    @Test
    public void testDoesLogContainInvalidAddress() {
        postOffice.sendEMail(address1, message1);
        assertFalse(postOffice.doesLogContain(address1, message2));
    }

    @Test
    public void testDoesLogContainInvalidMessage() {
        postOffice.sendEMail(address1, message1);
        assertFalse(postOffice.doesLogContain(address2, message1));
    }
}
