package cd.codevs.Resource.Management.Service.controller;

import cd.codevs.Resource.Management.Service.entity.Resource;
import cd.codevs.Resource.Management.Service.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping
    public ResponseEntity<Resource> createResource(
            @RequestBody Resource resource,
            @RequestHeader("Authorization") String apiKey,
            @RequestParam String tenantId
    ) {
        Resource createdResource = resourceService.createResource(resource, tenantId, apiKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResource);
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<Resource>> getResourcesByTenant(@PathVariable String tenantId) {
        return ResponseEntity.ok(resourceService.getResourcesByTenant(tenantId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Resource>> getResourceById(@PathVariable Long id) {
        return ResponseEntity.ok(resourceService.getResourceById(id));
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(
            @PathVariable Long id,
            @RequestBody Resource updatedResource) {
        return ResponseEntity.ok(resourceService.updateResource(id, updatedResource));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{resourceId}/availability")
    public boolean checkResourceAvailability(
            @PathVariable Long resourceId,
            @RequestParam("start") LocalDateTime start,
            @RequestParam("end") LocalDateTime end) {
        return resourceService.isResourceAvailable(resourceId, start, end);
    }
}
