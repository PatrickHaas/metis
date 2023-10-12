package io.metis.employees.domain.employee;

import io.metis.common.domain.AggregateRoot;
import io.metis.common.domain.employee.EmployeeId;
import io.metis.employees.domain.group.GroupId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@org.jmolecules.ddd.annotation.AggregateRoot
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Employee extends AggregateRoot<EmployeeId> {
    @org.jmolecules.ddd.annotation.Identity
    private final EmployeeId id;
    private FirstName firstName;
    private LastName lastName;
    private DateOfBirth dateOfBirth;
    private HiredOn hiredOn;
    private EmailAddress emailAddress;
    private String jobTitle;
    private final Set<GroupId> assignedGroups;

    Employee(EmployeeId id, FirstName firstName, LastName lastName, DateOfBirth dateOfBirth, EmailAddress emailAddress, String jobTitle) {
        this(id, firstName, lastName, dateOfBirth, null, emailAddress, jobTitle, new HashSet<>());
    }

    public void hire() {
        hiredOn = HiredOn.now();
        domainEvents().add(new EmployeeHired(getId(), getHiredOn().value()));
    }

    public void update(FirstName firstName, LastName lastName, DateOfBirth dateOfBirth, EmailAddress emailAddress, String jobTitle) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
        this.jobTitle = jobTitle;
        domainEvents().add(new EmployeeUpdated(getId()));
    }

    public void assignToGroup(GroupId groupId) {
        assignedGroups.add(groupId);
        domainEvents().add(new EmployeeAssignedToGroup(getId(), groupId));
    }
}
