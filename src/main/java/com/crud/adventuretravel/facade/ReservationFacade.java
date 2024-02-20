package com.crud.adventuretravel.facade;

import com.crud.adventuretravel.domain.Reservation;
import com.crud.adventuretravel.domain.ReservationDto;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.exception.ReservationNotFoundException;
import com.crud.adventuretravel.exception.TourNotFoundException;
import com.crud.adventuretravel.mapper.ReservationMapper;
import com.crud.adventuretravel.service.ReservationDBService;
import com.crud.adventuretravel.subscriber.ReservationStatusNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

    private final ReservationDBService reservationDBService;
    private final ReservationMapper reservationMapper;
    private final ReservationStatusNotifier reservationStatusNotifier;

    public List<ReservationDto> getReservations() {

        List<Reservation> reservationList = reservationDBService.getAllReservations().stream()
                .filter(r -> r.isActive())
                .collect(Collectors.toList());
        return reservationMapper.mapToReservationDtoList(reservationList);
    }

    public ReservationDto getReservationById(Long reservationId) throws ReservationNotFoundException {

        Reservation reservation = reservationDBService.getReservationById(reservationId);
        return reservationMapper.mapToReservationDto(reservation);
    }

    public void createReservation(ReservationDto reservationDto) throws TourNotFoundException, CustomerNotFoundException {

        Reservation reservation = reservationMapper.mapToReservation(reservationDto);
        reservationStatusNotifier.fetchReservation(reservation);
        reservationDBService.createReservation(reservation);
    }

    public void updateReservation(ReservationDto reservationDto) throws TourNotFoundException, CustomerNotFoundException {

        Reservation reservation = reservationMapper.mapToReservation(reservationDto);
        reservationStatusNotifier.fetchReservation(reservation);
        reservationDBService.updateReservation(reservation);
    }

    public void updateReservationDeactivate(ReservationDto reservationDto) throws ReservationNotFoundException {

        reservationDBService.updateReservationDeactivate(reservationDto);
    }

    public void deleteReservation(long reservationId) {

        reservationDBService.deleteReservation(reservationId);
    }
}
