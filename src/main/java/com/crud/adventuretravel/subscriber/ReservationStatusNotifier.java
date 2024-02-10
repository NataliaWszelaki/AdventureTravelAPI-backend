package com.crud.adventuretravel.subscriber;

import com.crud.adventuretravel.domain.Reservation;
import com.crud.adventuretravel.domain.ReservationStatus;
import com.crud.adventuretravel.emailservice.EmailService;
import com.crud.adventuretravel.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ReservationStatusNotifier implements NotifierObservable {

    private final EmailService emailService;
    private Map<String, SubscriberObserver> subscriberObserverMap;
    private final ReservationRepository reservationRepository;
    protected Reservation currentReservation;

    public void fetchReservation(Reservation reservation) {

        if (reservation.getReservationStatus() == ReservationStatus.NEW) {
            Subscriber subscriber = Subscriber.fromCustomer(reservation.getCustomer(), emailService);
            registerObserver(subscriber);
        }
        this.currentReservation = reservation;
        notifyObservers();
        if (reservation.getReservationStatus() == ReservationStatus.CANCELED
                || reservation.getReservationStatus() == ReservationStatus.CLOSED) {
            String email = reservation.getCustomer().getEmail();
            if (subscriberObserverMap.containsKey(email)) {
                removeObserver(subscriberObserverMap.get(email));
            }
        }
    }

    @Override
    public void registerObserver(SubscriberObserver subscriberObserver) {
        String email = subscriberObserver.getEmail();
        subscriberObserverMap.put(email, subscriberObserver);
    }

    @Override
    public void notifyObservers() {
        if (subscriberObserverMap.size() == 0) {
            List<Reservation> customerList = reservationRepository.findAll();
            subscriberObserverMap = customerList.stream()
                    .filter(r -> r.getReservationStatus().equals(ReservationStatus.CONFIRMED)
                            || r.getReservationStatus().equals(ReservationStatus.PENDING)
                            || r.getReservationStatus().equals(ReservationStatus.NEW))
                    .collect(Collectors.toMap(
                            r -> r.getCustomer().getEmail(),
                            r -> new Subscriber(r.getCustomer().getFirstName(), r.getCustomer().getEmail(), emailService)
                    ));
        }

        if (currentReservation != null) {
            String reservationEmail = currentReservation.getCustomer().getEmail();
            if (subscriberObserverMap.containsKey(reservationEmail)) {
                SubscriberObserver subscriberObserver = subscriberObserverMap.get(reservationEmail);
                subscriberObserver.update("Change in Reservation Status", "Status of your reservation has been changed to: "
                        + currentReservation.getReservationStatus());
            }
        }
    }

    @Override
    public void removeObserver(SubscriberObserver subscriberObserver) {

        String email = subscriberObserver.getEmail();
        subscriberObserverMap.remove(email);
    }
}
