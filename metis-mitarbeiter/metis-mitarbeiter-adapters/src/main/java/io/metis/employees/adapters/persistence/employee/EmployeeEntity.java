package io.metis.employees.adapters.persistence.employee;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "employees", schema = "employees")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class EmployeeEntity {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private LocalDate hiredOn;
    private String emailAddress;
    private String jobTitle;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(schema = "employees", name = "employee_assigned_groups", joinColumns = @JoinColumn(name = "employee_id"))
    private Set<UUID> assignedGroups;
}
