package com.crud.adventuretravel.subscriber;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.emailservice.EmailService;
import com.crud.adventuretravel.emailservice.Mail;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Subscriber implements SubscriberObserver {

    private final EmailService emailService;
    private String firstName;
    private String email;

    public Subscriber(String firstName, String email, EmailService emailService) {
        this.firstName = firstName;
        this.email = email;
        this.emailService = emailService;
    }

    @Override
    public void update(String subject, String message) {
        emailService.send(
                new Mail.MailBuilder()
                        .mailTo(email)
                        .subject("Adventure Travel Newsletter" + subject)
                        .message("Dear " + firstName + "! \n" + message)
                        .build());
    }

    public static Subscriber fromCustomer(Customer customer, EmailService emailService) {
        return new Subscriber(customer.getFirstName(), customer.getEmail(), emailService);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Subscriber subscriber = (Subscriber) obj;
        return Objects.equals(email, subscriber.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
