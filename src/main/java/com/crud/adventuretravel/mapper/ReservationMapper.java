package com.crud.adventuretravel.mapper;

import com.crud.adventuretravel.domain.Attraction;
import com.crud.adventuretravel.domain.Reservation;
import com.crud.adventuretravel.domain.ReservationDto;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.exception.TourNotFoundException;
import com.crud.adventuretravel.repository.AttractionRepository;
import com.crud.adventuretravel.repository.CustomerRepository;
import com.crud.adventuretravel.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationMapper {

    private final TourRepository tourRepository;
    private final CustomerRepository customerRepository;
    private final AttractionRepository attractionRepository;

    public ReservationDto mapToReservationDto(Reservation reservation) {

        return new ReservationDto(
                reservation.getId(),
                reservation.getTour().getId(),
                reservation.getCustomer().getId(),
                convertToLongSet(reservation.getAttractionList()),
                reservation.getReservationDate(),
                reservation.getPaymentStatus(),
                reservation.getReservationStatus()
        );
    }

    public Reservation mapToReservation(ReservationDto reservationDto) throws TourNotFoundException, CustomerNotFoundException {

        return new Reservation(
                reservationDto.getId(),
                tourRepository.findById(reservationDto.getTourId()).orElseThrow(TourNotFoundException::new),
                customerRepository.findById(reservationDto.getCustomerId()).orElseThrow(CustomerNotFoundException::new),
                convertToAttractionSet(reservationDto.getAttractionLongSet()),
                reservationDto.getReservationDate(),
                reservationDto.getPaymentStatus(),
                reservationDto.getReservationStatus()
        );
    }

    public List<ReservationDto> mapToReservationDtoList(List<Reservation> reservationList) {

        return reservationList.stream()
                .map(this::mapToReservationDto)
                .collect(Collectors.toList());
    }

    public Set<Long> convertToLongSet(Set<Attraction> attractionSet) {

        return attractionSet.stream()
                .map(a -> a.getId())
                .collect(Collectors.toSet());
    }

    public Set<Attraction> convertToAttractionSet(Set<Long> attractionLongSet) {

        return attractionLongSet.stream()
                .map(a -> attractionRepository.findById(a).get())
                .collect(Collectors.toSet());
    }
}
