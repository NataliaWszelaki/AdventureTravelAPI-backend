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
    private Map<Long, SubscriberObserver> subscriberObserverMap;
    private final ReservationRepository reservationRepository;
    protected Reservation currentReservation;

    public void fetchReservation(Reservation reservation) {
        this.currentReservation = reservation;
        Subscriber subscriber = Subscriber.fromCustomer(reservation.getCustomer(), emailService);
        if (reservation.getReservationStatus() == ReservationStatus.NEW) {

            registerObserver(subscriber);
        }
        notifyObservers();
        if (reservation.getReservationStatus() == ReservationStatus.CANCELED
                || reservation.getReservationStatus() == ReservationStatus.CLOSED) {
            removeObserver(subscriber);
        }
    }


    @Override
    public void registerObserver(SubscriberObserver subscriberObserver) {


        subscriberObserverMap.put(currentReservation.getId(), subscriberObserver);
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
                            Reservation::getId,
                            r -> new Subscriber(r.getCustomer().getFirstName(), r.getCustomer().getEmail(), emailService)
                    ));
        }
        if (currentReservation != null) {
            Long reservationId = currentReservation.getId();
            if (subscriberObserverMap.containsKey(reservationId)) {
                SubscriberObserver subscriberObserver = subscriberObserverMap.get(reservationId);
                subscriberObserver.update("Change in Reservation Status", "Status of your reservation has been changed to: "
                        + currentReservation.getReservationStatus());
            }
        }
    }

    @Override
    public void removeObserver(SubscriberObserver subscriberObserver) {

        subscriberObserverMap.remove(currentReservation.getId());
    }
}
