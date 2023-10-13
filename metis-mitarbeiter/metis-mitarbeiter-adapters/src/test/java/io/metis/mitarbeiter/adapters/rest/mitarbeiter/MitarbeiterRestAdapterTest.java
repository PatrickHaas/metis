package io.metis.mitarbeiter.adapters.rest.mitarbeiter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.metis.common.adapters.CommonConfiguration;
import io.metis.common.adapters.rest.CommonExceptionHandler;
import io.metis.common.adapters.security.SecurityConfiguration;
import io.metis.mitarbeiter.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.mitarbeiter.domain.mitarbeiter.Mitarbeiter;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        Mockito.when(primaryPort.findAll()).thenReturn(List.of(tony, bruce));
        List<MitarbeiterMessage> mitarbeiterMessages = objectMapper.readValue(mockMvc.perform(get("/rest/v1/mitarbeiter")
                        .with(csrf())
                        .with(jwt().authorities(new SimpleGrantedAuthority("employees:employees:list"))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
        });
        assertThat(mitarbeiterMessages).hasSize(2);
        assertThat(mitarbeiterMessages).containsExactlyInAnyOrder(
                MitarbeiterMessage.from(tony),
                MitarbeiterMessage.from(bruce)
        );
    }

}
