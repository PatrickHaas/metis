package io.metis.personal.adapters.persistence;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class KeycloakAttributeAccessor {
    public String getAttributeValue(Map<String, List<String>> attributes, String attribute) {
        return Optional.ofNullable(attributes)
                .map(attr -> attr.get(attribute))
                .map(attr -> attr.get(0))
                .orElse(null);
    }
}
