package cd.codevs.Resource.Management.Service.repository;

import cd.codevs.Resource.Management.Service.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByTenantId(String tenantId);
}
