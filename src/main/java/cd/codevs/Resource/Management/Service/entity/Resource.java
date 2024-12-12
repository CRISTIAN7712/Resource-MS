package cd.codevs.Resource.Management.Service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int capacity;
    private String tenantId; // Asociar con el tenant

    // Getters y setters
}

