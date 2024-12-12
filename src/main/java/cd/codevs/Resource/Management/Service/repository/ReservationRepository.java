package cd.codevs.Resource.Management.Service.repository;

import cd.codevs.Resource.Management.Service.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByTenantId(String tenantId);
}
