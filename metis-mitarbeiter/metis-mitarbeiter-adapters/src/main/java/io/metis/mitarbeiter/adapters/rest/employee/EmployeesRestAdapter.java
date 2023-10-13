package io.metis.mitarbeiter.adapters.rest.employee;


import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.mitarbeiter.application.mitarbeiter.AktualisiereMitarbeiterdatenCommand;
import io.metis.mitarbeiter.application.mitarbeiter.MitarbeiterEinerGruppeZuweisenCommand;
import io.metis.mitarbeiter.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.mitarbeiter.application.mitarbeiter.StelleMitarbeiterEinCommand;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
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

    private final MitarbeiterPrimaryPort primaryPort;
    private final RestEmployeeMapper mapper;

    @GetMapping
    List<EmployeeMessage> findAll() {
        return primaryPort.findAll().stream()
                .map(mapper::to)
                .toList();
    }

    @GetMapping("{id}")
    EmployeeMessage find(@PathVariable("id") UUID id) {
        return mapper.to(primaryPort.getById(new MitarbeiterId(id)));
    }

    @PostMapping
    EmployeeMessage hire(@RequestBody @Valid HireEmployeeMessage message) {
        StelleMitarbeiterEinCommand command = new StelleMitarbeiterEinCommand(message.firstName(), message.lastName(), message.dateOfBirth(), message.emailAddress(), message.jobTitle());
        return mapper.to(primaryPort.stelleEin(command));
    }

    @PutMapping("{id}")
    EmployeeMessage update(@PathVariable("id") UUID id, @RequestBody @Valid HireEmployeeMessage message) {
        return mapper.to(primaryPort.aktualisiereDaten(new AktualisiereMitarbeiterdatenCommand(new MitarbeiterId(id), message.firstName(), message.lastName(), message.dateOfBirth(), message.emailAddress(), message.jobTitle())));
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable("id") UUID id) {
        primaryPort.deleteById(new MitarbeiterId(id));
    }

    @PostMapping("{id}/assigned-groups")
    EmployeeMessage assignToGroup(@PathVariable("id") UUID id, @RequestBody @Valid AssignToGroupMessage message) {
        return mapper.to(primaryPort.weiseGruppeZu(new MitarbeiterEinerGruppeZuweisenCommand(new GruppeId(message.groupId()), new MitarbeiterId(id))));
    }
}
