package io.metis.employees.domain.user;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserFactoryTest {

    @Test
    void create_shouldCreateNewUser() {
        UUID employeeId = UUID.randomUUID();
        User user = new UserFactory().create(employeeId, "Tony", "Stark", "tony@avengers.com");

        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmployeeId().value()).isEqualTo(employeeId);
        assertThat(user.getFirstName()).isEqualTo("Tony");
        assertThat(user.getLastName()).isEqualTo("Stark");
        assertThat(user.getEmail()).isEqualTo("tony@avengers.com");
    }

}
