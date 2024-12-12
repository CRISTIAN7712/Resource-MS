package cd.codevs.Resource.Management.Service.controller;

import cd.codevs.Resource.Management.Service.entity.Reservation;
import cd.codevs.Resource.Management.Service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/{tenantId}")
    public ResponseEntity<Reservation> createReservation(
            @RequestBody Reservation reservation,
            @PathVariable String tenantId) {
        return ResponseEntity.ok(reservationService.createReservation(reservation, tenantId));
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<List<Reservation>> getReservationsByTenant(@PathVariable String tenantId) {
        return ResponseEntity.ok(reservationService.getReservationsByTenant(tenantId));
    }
}
