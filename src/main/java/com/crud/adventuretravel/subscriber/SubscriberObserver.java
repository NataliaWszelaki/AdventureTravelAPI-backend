package com.crud.adventuretravel.subscriber;

public interface SubscriberObserver {
    void update(String subject, String message);

    String getEmail();
}