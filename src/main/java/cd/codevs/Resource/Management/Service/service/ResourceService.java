package cd.codevs.Resource.Management.Service.service;

import cd.codevs.Resource.Management.Service.entity.Resource;
import cd.codevs.Resource.Management.Service.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String tenantServiceUrl = "http://localhost:8081/api/ms/tenants";

    public Resource createResource(Resource resource, String tenantId) {
        validateTenant(tenantId); // Validar tenant antes de continuar
        resource.setTenantId(tenantId);
        return resourceRepository.save(resource);
    }

    public List<Resource> getResourcesByTenant(String tenantId) {
        return resourceRepository.findByTenantId(tenantId);
    }

    private void validateTenant(String tenantId) {
        String url = tenantServiceUrl + "/" + tenantId + "/exists";
        Boolean tenantExists = restTemplate.getForObject(url, Boolean.class);
        if (!Boolean.TRUE.equals(tenantExists)) {
            throw new IllegalArgumentException("Tenant not found: " + tenantId);
        }
    }
}
