package io.metis.mitarbeiter.adapters.persistence;

import lombok.Data;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakConfiguration {
  private String url;
  private String realm;
  private String username;
  private String password;
  private List<String> userCreationActions;

  @Bean
  Keycloak keycloak() {
    return KeycloakBuilder.builder()
      .serverUrl(url)
      .grantType(OAuth2Constants.PASSWORD)
      .realm("master")
      .clientId("admin-cli")
      .username(username)
      .password(password)
      .build();
  }
}
