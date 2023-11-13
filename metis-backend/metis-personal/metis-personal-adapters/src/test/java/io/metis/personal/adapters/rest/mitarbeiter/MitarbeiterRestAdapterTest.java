package io.metis.personal.adapters.rest.mitarbeiter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.metis.common.adapters.CommonConfiguration;
import io.metis.common.adapters.rest.CommonExceptionHandler;
import io.metis.common.adapters.security.SecurityConfiguration;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.personal.application.mitarbeiter.MitarbeiterNotFoundException;
import io.metis.personal.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.personal.application.mitarbeiter.StelleMitarbeiterEinCommand;
import io.metis.personal.domain.mitarbeiter.Mitarbeiter;
import io.metis.personal.domain.mitarbeiter.MitarbeiterFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {MitarbeiterRestAdapter.class})
@AutoConfigureMockMvc
@Import({CommonConfiguration.class, CommonExceptionHandler.class, SecurityConfiguration.class, MitarbeiterRestConfiguration.class})
@ActiveProfiles("test")
class MitarbeiterRestAdapterTest {

    @MockBean
    private JwtDecoder jwtDecoder;
    @MockBean
    private MitarbeiterPrimaryPort primaryPort;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MitarbeiterFactory factory;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void findAll_shouldReturnUnauthorized_whenNoAuthCouldBeFound() throws Exception {
        mockMvc.perform(get("/rest/v1/mitarbeiter"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findAll_shouldReturnForbidden_whenAuthorityIsMissing() throws Exception {
        mockMvc.perform(get("/rest/v1/mitarbeiter")
                        .with(csrf())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    void findAll_shouldReturnAllEmployees() throws Exception {
        Mitarbeiter tony = factory.create(UUID.randomUUID(), "Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man");
        tony.einstellen();
        Mitarbeiter bruce = factory.create(UUID.randomUUID(), "Bruce", "Banner", LocalDate.of(1970, 5, 29), "bruce@avengers.com", "Hulk");
        bruce.einstellen();
        when(primaryPort.findAll()).thenReturn(List.of(tony, bruce));
        List<MitarbeiterMessage> mitarbeiterMessages = objectMapper.readValue(mockMvc.perform(get("/rest/v1/mitarbeiter")
                        .with(csrf())
                        .with(jwt().authorities(new SimpleGrantedAuthority("personnel:employees:list"))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
        });
        Assertions.assertThat(mitarbeiterMessages).hasSize(2);
        Assertions.assertThat(mitarbeiterMessages).containsExactlyInAnyOrder(
                MitarbeiterMessage.from(tony),
                MitarbeiterMessage.from(bruce)
        );
    }

    @Test
    void find_shouldReturnUnauthorized_whenNoAuthCouldBeFound() throws Exception {
        mockMvc.perform(get("/rest/v1/mitarbeiter/{id}", UUID.randomUUID()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void find_shouldReturnForbidden_whenAuthorityIsMissing() throws Exception {
        mockMvc.perform(get("/rest/v1/mitarbeiter/{id}", UUID.randomUUID())
                        .with(csrf())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    void find_shouldReturnNotFound_whenEmployeeCouldNotBeFound() throws Exception {
        MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
        when(primaryPort.getById(mitarbeiterId)).thenThrow(new MitarbeiterNotFoundException(mitarbeiterId));
        mockMvc.perform(get("/rest/v1/mitarbeiter/{id}", mitarbeiterId.value())
                        .with(csrf())
                        .with(jwt().authorities(new SimpleGrantedAuthority("personnel:employees:show"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void find_shouldReturnEmployee() throws Exception {
        MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
        Mitarbeiter tony = factory.create(mitarbeiterId.value(), "Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man");
        tony.einstellen();
        when(primaryPort.getById(mitarbeiterId)).thenReturn(tony);
        MitarbeiterMessage mitarbeiterMessage = objectMapper.readValue(mockMvc.perform(get("/rest/v1/mitarbeiter/{id}", mitarbeiterId.value())
                        .with(csrf())
                        .with(jwt().authorities(new SimpleGrantedAuthority("personnel:employees:show"))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), MitarbeiterMessage.class);
        assertThat(mitarbeiterMessage).isEqualTo(MitarbeiterMessage.from(tony));
    }

    @Test
    void einstellen_shouldReturnUnauthorized_whenNoAuthCouldBeFound() throws Exception {
        mockMvc.perform(post("/rest/v1/mitarbeiter")
                        .content(objectMapper.writeValueAsBytes(new StelleMitarbeiterEinMessage("Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void einstellen_shouldReturnForbidden_whenAuthorityIsMissing() throws Exception {
        mockMvc.perform(post("/rest/v1/mitarbeiter")
                        .content(objectMapper.writeValueAsBytes(new StelleMitarbeiterEinMessage("Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    void einstellen_shouldReturnBadRequest_whenFirstNameIsNull() throws Exception {
        mockMvc.perform(post("/rest/v1/mitarbeiter")
                        .content(objectMapper.writeValueAsBytes(new StelleMitarbeiterEinMessage(null, "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void einstellen_shouldReturnBadRequest_whenFirstNameIsEmpty() throws Exception {
        mockMvc.perform(post("/rest/v1/mitarbeiter")
                        .content(objectMapper.writeValueAsBytes(new StelleMitarbeiterEinMessage("", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void einstellen_shouldReturnBadRequest_whenFirstNameIsBlank() throws Exception {
        mockMvc.perform(post("/rest/v1/mitarbeiter")
                        .content(objectMapper.writeValueAsBytes(new StelleMitarbeiterEinMessage("   ", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }


    @Test
    void einstellen_shouldReturnBadRequest_whenLastNameIsNull() throws Exception {
        mockMvc.perform(post("/rest/v1/mitarbeiter")
                        .content(objectMapper.writeValueAsBytes(new StelleMitarbeiterEinMessage("Tony", null, LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void einstellen_shouldReturnBadRequest_whenLastNameIsEmpty() throws Exception {
        mockMvc.perform(post("/rest/v1/mitarbeiter")
                        .content(objectMapper.writeValueAsBytes(new StelleMitarbeiterEinMessage("Tony", "", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void einstellen_shouldReturnBadRequest_whenLastNameIsBlank() throws Exception {
        mockMvc.perform(post("/rest/v1/mitarbeiter")
                        .content(objectMapper.writeValueAsBytes(new StelleMitarbeiterEinMessage("Tony", "   ", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void einstellen_shouldReturnBadRequest_whenDateOfBirthIsNull() throws Exception {
        mockMvc.perform(post("/rest/v1/mitarbeiter")
                        .content(objectMapper.writeValueAsBytes(new StelleMitarbeiterEinMessage("Tony", "Stark", null, "tony@avengers.com", "Iron-Man")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void einstellen_shouldReturnBadRequest_whenEmailAddressIsNull() throws Exception {
        mockMvc.perform(post("/rest/v1/mitarbeiter")
                        .content(objectMapper.writeValueAsBytes(new StelleMitarbeiterEinMessage("Tony", "Stark", LocalDate.of(1970, 5, 29), null, "Iron-Man")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void einstellen_shouldReturnBadRequest_whenEmailAddressIsInvalid() throws Exception {
        mockMvc.perform(post("/rest/v1/mitarbeiter")
                        .content(objectMapper.writeValueAsBytes(new StelleMitarbeiterEinMessage("Tony", "Stark", LocalDate.of(1970, 5, 29), "invalid-email", "Iron-Man")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(jwt()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void einstellen_shouldSuccessfullyHireTheEmployee() throws Exception {
        MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
        Mitarbeiter tony = factory.create(mitarbeiterId.value(), "Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man");
        tony.einstellen();
        when(primaryPort.stelleEin(new StelleMitarbeiterEinCommand("Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man"))).thenReturn(tony);
        MitarbeiterMessage mitarbeiterMessage = objectMapper.readValue(mockMvc.perform(post("/rest/v1/mitarbeiter")
                        .content(objectMapper.writeValueAsBytes(new StelleMitarbeiterEinMessage("Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(jwt().authorities(new SimpleGrantedAuthority("personnel:employees:hire"))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), MitarbeiterMessage.class);
        assertThat(mitarbeiterMessage).isEqualTo(MitarbeiterMessage.from(tony));
    }
}
