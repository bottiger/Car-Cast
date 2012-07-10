package com.bottiger.cc.services;

import java.util.List;

import com.bottiger.cc.core.Subscription;

public interface SubscriptionHelper {
    public boolean addSubscription(Subscription toAdd);
    public void deleteAllSubscriptions();
    public boolean editSubscription(Subscription original, Subscription updated);
    public List<Subscription> getSubscriptions();
    public boolean removeSubscription(Subscription toRemove);
    public List<Subscription> resetToDemoSubscriptions();
	boolean toggleSubscription(Subscription toToggle);
}
