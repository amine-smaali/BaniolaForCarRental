package com.projet.inscription_connexion.buisness.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.projet.inscription_connexion.doa.Entity.Client;
import com.projet.inscription_connexion.doa.Entity.Reservation;
import com.projet.inscription_connexion.doa.Repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    public Reservation getReservationById(Long id) {
    return reservationRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Reservation not found"));
}

    

    public List<Reservation> getReservationbyClient(Client client) {
        return reservationRepository.findAllByClient(client);
    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    public Reservation addReservation(Reservation reservation) {
        return this.reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Long id,Reservation updatedReservation){

        Reservation res = this.reservationRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("reservation not found"));

        res.setDate_deb(updatedReservation.getDate_deb());
        res.setDate_fin(updatedReservation.getDate_fin());
        res.setCar(updatedReservation.getCar());
        res.setClient(updatedReservation.getClient());
        res.setStatut(updatedReservation.getStatut());
        return this.reservationRepository.save(res);


    }

    public void deleteReservation(Long id) {
        if (this.reservationRepository.existsById(id)) {
            this.reservationRepository.deleteById(id);
        }
    }

    public List<Reservation> getUnconfirmedReservations(){
        return this.reservationRepository.findByStatut("en attente") ;
    }

    
    public double calculatePrice(float pricePerDay, LocalDate start, LocalDate end) {
        long days = ChronoUnit.DAYS.between(start, end);
        return pricePerDay * days;
    }

    public void manageReservation(Long idReservation,String statut){
        Reservation res = this.reservationRepository.findById(idReservation).get();
        res.setStatut(statut);
        updateReservation(idReservation, res);
    }

    

    

   

}
