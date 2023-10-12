package io.metis.employees.adapters.rest.employee;


import io.metis.common.domain.employee.EmployeeId;
import io.metis.employees.application.employee.AssignToGroupCommand;
import io.metis.employees.application.employee.EmployeePrimaryPort;
import io.metis.employees.application.employee.HireEmployeeCommand;
import io.metis.employees.application.employee.UpdateEmployeeCommand;
import io.metis.employees.domain.group.GroupId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@PrimaryAdapter
@RestController
@RequestMapping("rest/v1/employees")
@RequiredArgsConstructor
class EmployeesRestAdapter {

    private final EmployeePrimaryPort primaryPort;
    private final RestEmployeeMapper mapper;

    @GetMapping
    List<EmployeeMessage> findAll() {
        return primaryPort.findAll().stream()
                .map(mapper::to)
                .toList();
    }

    @GetMapping("{id}")
    EmployeeMessage find(@PathVariable("id") UUID id) {
        return mapper.to(primaryPort.findById(new EmployeeId(id)));
    }

    @PostMapping
    EmployeeMessage hire(@RequestBody @Valid HireEmployeeMessage message) {
        HireEmployeeCommand command = new HireEmployeeCommand(message.firstName(), message.lastName(), message.dateOfBirth(), message.emailAddress(), message.jobTitle());
        return mapper.to(primaryPort.hire(command));
    }

    @PutMapping("{id}")
    EmployeeMessage update(@PathVariable("id") UUID id, @RequestBody @Valid HireEmployeeMessage message) {
        return mapper.to(primaryPort.update(new UpdateEmployeeCommand(new EmployeeId(id), message.firstName(), message.lastName(), message.dateOfBirth(), message.emailAddress(), message.jobTitle())));
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable("id") UUID id) {
        primaryPort.deleteById(new EmployeeId(id));
    }

    @PostMapping("{id}/assigned-groups")
    EmployeeMessage assignToGroup(@PathVariable("id") UUID id, @RequestBody @Valid AssignToGroupMessage message) {
        return mapper.to(primaryPort.assignToGroup(new AssignToGroupCommand(new GroupId(message.groupId()), new EmployeeId(id))));
    }
}
