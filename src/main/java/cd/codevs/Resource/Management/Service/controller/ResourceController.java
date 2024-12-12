package cd.codevs.Resource.Management.Service.controller;

import cd.codevs.Resource.Management.Service.entity.Resource;
import cd.codevs.Resource.Management.Service.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/{tenantId}")
    public ResponseEntity<Resource> createResource(
            @RequestBody Resource resource,
            @PathVariable String tenantId) {
        return ResponseEntity.ok(resourceService.createResource(resource, tenantId));
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<List<Resource>> getResourcesByTenant(@PathVariable String tenantId) {
        return ResponseEntity.ok(resourceService.getResourcesByTenant(tenantId));
    }
}
