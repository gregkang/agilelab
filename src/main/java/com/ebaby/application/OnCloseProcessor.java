package com.ebaby.application;

import com.ebaby.application.Auction;

public interface OnCloseProcessor {
    void process(Auction auction);
}
