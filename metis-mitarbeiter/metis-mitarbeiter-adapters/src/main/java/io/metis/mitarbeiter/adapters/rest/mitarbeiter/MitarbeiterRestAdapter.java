package io.metis.mitarbeiter.adapters.rest.mitarbeiter;


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
@RequestMapping("rest/v1/mitarbeiter")
@RequiredArgsConstructor
class MitarbeiterRestAdapter {

    private final MitarbeiterPrimaryPort primaryPort;
    private final MitarbeiterMapper mapper;

    @GetMapping
    List<MitarbeiterMessage> findAll() {
        return primaryPort.findAll().stream()
                .map(mapper::to)
                .toList();
    }

    @GetMapping("{id}")
    MitarbeiterMessage find(@PathVariable("id") UUID id) {
        return mapper.to(primaryPort.getById(new MitarbeiterId(id)));
    }

    @PostMapping
    MitarbeiterMessage einstellen(@RequestBody @Valid StelleMitarbeiterEinMessage message) {
        StelleMitarbeiterEinCommand command = new StelleMitarbeiterEinCommand(message.vorname(), message.nachname(), message.geburtsdatum(), message.emailAdresse(), message.jobTitel());
        return mapper.to(primaryPort.stelleEin(command));
    }

    @PutMapping("{id}")
    MitarbeiterMessage aktualisiereMitarbeiterdaten(@PathVariable("id") UUID id, @RequestBody @Valid StelleMitarbeiterEinMessage message) {
        return mapper.to(primaryPort.aktualisiereDaten(new AktualisiereMitarbeiterdatenCommand(new MitarbeiterId(id), message.vorname(), message.nachname(), message.geburtsdatum(), message.emailAdresse(), message.jobTitel())));
    }

    @DeleteMapping("{id}")
    void deleteById(@PathVariable("id") UUID id) {
        primaryPort.deleteById(new MitarbeiterId(id));
    }

    @PostMapping("{id}/zugewiesene-gruppen")
    MitarbeiterMessage weiseGruppeZu(@PathVariable("id") UUID id, @RequestBody @Valid WeiseMitarbeiterEinerGruppeZuMessage message) {
        return mapper.to(primaryPort.weiseGruppeZu(new MitarbeiterEinerGruppeZuweisenCommand(new GruppeId(message.groupId()), new MitarbeiterId(id))));
    }
}
