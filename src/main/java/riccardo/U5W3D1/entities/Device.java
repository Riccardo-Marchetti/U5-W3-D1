package riccardo.U5W3D1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    @GeneratedValue
    @Setter (AccessLevel.NONE)
    private UUID id;

    private String type;

    private String status;

    private LocalDate maintenanceStartDate;

    private LocalDate maintenanceEndDate;

    @ManyToOne
    @JoinColumn (name = "employee_id")
    private Employee employee;

    public Device(String type) {
        this.type = type;
        this.status = "Available";
        this.employee = null;
        this.maintenanceStartDate = null;
        this.maintenanceEndDate = null;
    }
}
