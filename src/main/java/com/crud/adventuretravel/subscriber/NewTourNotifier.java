package com.crud.adventuretravel.subscriber;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.emailservice.EmailService;
import com.crud.adventuretravel.repository.CustomerRepository;
import com.crud.adventuretravel.repository.TourRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class NewTourNotifier implements NotifierObservable {

    private List<SubscriberObserver> subscriberObserverList;
    private final EmailService emailService;
    private final CustomerRepository customerRepository;
    private final TourRepository tourRepository;

    public void checkSubscriptionNewCustomer(Customer customer) {

        boolean isSubscriber = customer.isSubscriber();
        Subscriber subscriber = Subscriber.fromCustomer(customer, emailService);
        if (isSubscriber) registerObserver(subscriber);
    }

    public void checkSubscriptionUpdatedCustomer(Customer customer) {

        boolean isSubscriber = customer.isSubscriber();
        Subscriber subscriber = Subscriber.fromCustomer(customer, emailService);
        if (isSubscriber) {
            registerObserver(subscriber);
        } else {
            removeObserver(subscriber);
        }
    }

    @Override
    public void registerObserver(SubscriberObserver subscriberObserver) {

        subscriberObserverList.add(subscriberObserver);
    }

    @Override
    public void notifyObservers() {

        if (subscriberObserverList.size() == 0) {
            List<Customer> customerList = customerRepository.findAllBySubscriber(true);
            subscriberObserverList = customerList.stream()
                    .map(c -> new Subscriber(c.getFirstName(), c.getEmail(), emailService))
                    .collect(Collectors.toList());
        }
        for (SubscriberObserver subscriberObserver : subscriberObserverList) {
            subscriberObserver.update("Exciting News: New Tour is available on our Website!",
                    "Check the details no Our Website!\nThe number of available tours is: " + tourRepository.count());
        }

    }

    @Override
    public void removeObserver(SubscriberObserver subscriberObserver) {

        subscriberObserverList.remove(subscriberObserver);
    }
}
