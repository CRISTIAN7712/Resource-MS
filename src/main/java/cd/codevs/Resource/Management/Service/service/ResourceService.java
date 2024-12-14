package cd.codevs.Resource.Management.Service.service;

import cd.codevs.Resource.Management.Service.entity.Resource;
import cd.codevs.Resource.Management.Service.repository.ReservationRepository;
import cd.codevs.Resource.Management.Service.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Crear un recurso
    public Resource createResource(Resource resource, String tenantId, String apiKey) {
        // Validar la API Key con el microservicio de Tenant
        if (!isApiKeyValid(apiKey, tenantId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API Key or Tenant ID");
        }

        // Si la validación es exitosa, guardar el recurso
        resource.setTenantId(tenantId);
        return resourceRepository.save(resource);
    }

    // Método para validar la API Key con el microservicio de Tenant
    private boolean isApiKeyValid(String apiKey, String tenantId) {
        try {
            String tenantServiceUrl = "http://localhost:8080/api/tenants/validate"; // Cambiar si es necesario
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", apiKey);

            // Construir la solicitud
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            // Realizar la llamada GET
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    tenantServiceUrl + "?tenantId=" + tenantId,
                    HttpMethod.GET,
                    requestEntity,
                    Boolean.class
            );

            return Boolean.TRUE.equals(response.getBody());
        } catch (Exception e) {
            e.printStackTrace(); // Para depuración
            return false;
        }
    }

    // Obtener todos los recursos por Tenant ID
    public List<Resource> getResourcesByTenant(String tenantId) {
        return resourceRepository.findByTenantId(tenantId);
    }

    // Obtener recurso por ID
    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    // Listar todos los recursos
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    // Actualizar un recurso
    public Resource updateResource(Long id, Resource updatedResource) {
        Optional<Resource> existingResourceOpt = resourceRepository.findById(id);
        if (existingResourceOpt.isPresent()) {
            Resource existingResource = existingResourceOpt.get();
            existingResource.setName(updatedResource.getName());
            existingResource.setDescription(updatedResource.getDescription());
            existingResource.setCapacity(updatedResource.getCapacity());
            existingResource.setTenantId(updatedResource.getTenantId());
            return resourceRepository.save(existingResource);
        } else {
            throw new RuntimeException("Resource with ID " + id + " not found");
        }
    }

    // Eliminar un recurso
    public void deleteResource(Long id) {
        if (resourceRepository.existsById(id)) {
            resourceRepository.deleteById(id);
        } else {
            throw new RuntimeException("Resource with ID " + id + " not found");
        }
    }

    public boolean isResourceAvailable(Long resourceId, LocalDateTime start, LocalDateTime end) {
        // Verifica si existen reservas que se solapen con el recurso en el intervalo de tiempo
        return reservationRepository.findByResourceIdAndReservationTimeBetween(resourceId, start, end).isEmpty();
    }
}

