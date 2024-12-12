package cd.codevs.Resource.Management.Service.service;

import cd.codevs.Resource.Management.Service.entity.Reservation;
import cd.codevs.Resource.Management.Service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation createReservation(Reservation reservation, String tenantId) {
        reservation.setTenantId(tenantId);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByTenant(String tenantId) {
        return reservationRepository.findByTenantId(tenantId);
    }
}
