package com.crud.adventuretravel.subscriber;

public interface NotifierObservable {

    void registerObserver(SubscriberObserver subscriberObserver);
    void notifyObservers();
    void removeObserver(SubscriberObserver subscriberObserver);
}