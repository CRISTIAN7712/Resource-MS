package cd.codevs.Resource.Management.Service.controller;

import cd.codevs.Resource.Management.Service.entity.Reservation;
import cd.codevs.Resource.Management.Service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Crear una reserva
    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestBody Reservation reservation,
            @RequestHeader("Authorization") String tenantId
    ) {
        return ResponseEntity.ok(reservationService.createReservation(reservation, tenantId));
    }

    // Obtener reservas por Tenant ID
    @GetMapping
    public ResponseEntity<List<Reservation>> getReservationsByTenant(
            @RequestParam String tenantId
    ) {
        return ResponseEntity.ok(reservationService.getReservationsByTenant(tenantId));
    }

    // Obtener reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Reservation>> getReservationById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    // Buscar reservas por Tenant ID y rango de fechas
    @GetMapping("/by-date-range")
    public ResponseEntity<List<Reservation>> getReservationsByTenantAndDateRange(
            @RequestParam String tenantId,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end
    ) {
        return ResponseEntity.ok(reservationService.getReservationsByTenantAndDateRange(tenantId, start, end));
    }
}
