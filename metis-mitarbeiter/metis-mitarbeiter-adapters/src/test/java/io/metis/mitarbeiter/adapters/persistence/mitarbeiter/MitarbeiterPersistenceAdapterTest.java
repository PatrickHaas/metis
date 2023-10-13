package io.metis.mitarbeiter.adapters.persistence.mitarbeiter;

import io.metis.common.adapters.CommonConfiguration;
import io.metis.mitarbeiter.domain.mitarbeiter.Mitarbeiter;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        MitarbeiterPersistenceAdapter.class
}), properties = "spring.liquibase.change-log=db/changelog/employees.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({CommonConfiguration.class, TestDataBaseConfiguration.class, MitarbeiterPersistenceConfiguration.class})
@ActiveProfiles("test")
class MitarbeiterPersistenceAdapterTest {

    private final MitarbeiterFactory mitarbeiterFactory = new MitarbeiterFactory();

    @Autowired
    private MitarbeiterPersistenceAdapter adapter;

    @Test
    void save_shouldSaveAndReturnSuccessfully() {
        Mitarbeiter mitarbeiter = mitarbeiterFactory.create(UUID.randomUUID(), "Tony", "Stark", LocalDate.of(1970, 5, 29), LocalDate.now(), "tony@avengers.com", "Iron-Man", Set.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        ));
        Mitarbeiter savedMitarbeiter = adapter.save(mitarbeiter);

        assertThat(savedMitarbeiter.getId()).isEqualTo(mitarbeiter.getId());
        assertThat(savedMitarbeiter.getVorname()).isEqualTo(mitarbeiter.getVorname());
        assertThat(savedMitarbeiter.getNachname()).isEqualTo(mitarbeiter.getNachname());
        assertThat(savedMitarbeiter.getGeburtsdatum()).isEqualTo(mitarbeiter.getGeburtsdatum());
        assertThat(savedMitarbeiter.getEinstelltAm()).isEqualTo(mitarbeiter.getEinstelltAm());
        assertThat(savedMitarbeiter.getEmailAdresse()).isEqualTo(mitarbeiter.getEmailAdresse());
        assertThat(savedMitarbeiter.getJobTitel()).isEqualTo(mitarbeiter.getJobTitel());
        assertThat(savedMitarbeiter.getZugewieseneGruppen()).isEqualTo(mitarbeiter.getZugewieseneGruppen());
    }


}
