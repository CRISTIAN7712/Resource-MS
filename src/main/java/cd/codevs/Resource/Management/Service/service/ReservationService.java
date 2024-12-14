package cd.codevs.Resource.Management.Service.service;

import cd.codevs.Resource.Management.Service.entity.Reservation;
import cd.codevs.Resource.Management.Service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    // Crear una reserva
    public Reservation createReservation(Reservation reservation, String tenantId) {
        reservation.setTenantId(tenantId);
        return reservationRepository.save(reservation);
    }

    // Obtener todas las reservas por Tenant ID
    public List<Reservation> getReservationsByTenant(String tenantId) {
        return reservationRepository.findByTenantId(tenantId);
    }

    // Obtener reserva por ID
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    // Listar todas las reservas
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Actualizar una reserva
    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        Optional<Reservation> existingReservationOpt = reservationRepository.findById(id);
        if (existingReservationOpt.isPresent()) {
            Reservation existingReservation = existingReservationOpt.get();
            existingReservation.setUserName(updatedReservation.getUserName());
            existingReservation.setResourceName(updatedReservation.getResourceName());
            existingReservation.setReservationTime(updatedReservation.getReservationTime());
            existingReservation.setTenantId(updatedReservation.getTenantId());
            return reservationRepository.save(existingReservation);
        } else {
            throw new RuntimeException("Reservation with ID " + id + " not found");
        }
    }

    // Eliminar una reserva
    public void deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation with ID " + id + " not found");
        }
    }

    // Buscar reservas entre un rango de fechas por Tenant ID
    public List<Reservation> getReservationsByTenantAndDateRange(String tenantId, LocalDateTime start, LocalDateTime end) {
        return reservationRepository.findByTenantIdAndReservationTimeBetween(tenantId, start, end);
    }
}
