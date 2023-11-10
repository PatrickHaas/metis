package io.metis.mitarbeiter.application.benutzerkonto;

import io.metis.common.domain.EventHandlerRegistry;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.mitarbeiter.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.mitarbeiter.domain.benutzerkonto.Benutzerkonto;
import io.metis.mitarbeiter.domain.benutzerkonto.BenutzerkontoRepository;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import io.metis.mitarbeiter.domain.mitarbeiter.Mitarbeiter;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterEinerGruppeZugewiesen;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterEingestellt;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BenutzerkontoServiceTest {

    @Mock
    private BenutzerkontoRepository repository;
    @Mock
    private MitarbeiterPrimaryPort mitarbeiterPrimaryPort;
    @Mock
    private EventHandlerRegistry eventHandlerRegistry;

    @Spy
    private MitarbeiterFactory mitarbeiterFactory;

    private BenutzerkontoService service;

    @BeforeEach
    void createService() {
        service = new BenutzerkontoService(repository, mitarbeiterPrimaryPort, eventHandlerRegistry);
        verify(eventHandlerRegistry).subscribe(Mockito.same(MitarbeiterEingestellt.class), Mockito.any());
        verify(eventHandlerRegistry).subscribe(Mockito.same(MitarbeiterEinerGruppeZugewiesen.class), Mockito.any());
    }

    @Test
    void weiseBenutzerRolleZu() {
        MitarbeiterEinerGruppeZugewiesen event = new MitarbeiterEinerGruppeZugewiesen(new MitarbeiterId(UUID.randomUUID()), new GruppeId(UUID.randomUUID()));
        service.weiseBenutzerRolleZu(event);
        verify(repository).assignGroupByEmployeeId(event.mitarbeiterId(), event.gruppeId());
    }

    @Test
    void erstelleBenutzerkonto() {
        MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
        Mitarbeiter mitarbeiter = mitarbeiterFactory.create(mitarbeiterId.value(), "Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man");
        when(mitarbeiterPrimaryPort.getById(mitarbeiterId))
                .thenReturn(mitarbeiter);
        service.erstelleBenutzerkonto(new MitarbeiterEingestellt(mitarbeiterId, LocalDate.now()));
        ArgumentCaptor<Benutzerkonto> benutzerkontoArgumentCaptor = ArgumentCaptor.forClass(Benutzerkonto.class);
        verify(repository).save(benutzerkontoArgumentCaptor.capture());
        Benutzerkonto benutzerkonto = benutzerkontoArgumentCaptor.getValue();
        assertThat(benutzerkonto.getMitarbeiterId()).isEqualTo(mitarbeiterId);
        assertThat(benutzerkonto.getVorname()).isEqualTo(mitarbeiter.getVorname().value());
        assertThat(benutzerkonto.getNachname()).isEqualTo(mitarbeiter.getNachname().value());
        assertThat(benutzerkonto.getEmailAdresse()).isEqualTo(mitarbeiter.getEmailAdresse().value());
    }
}
