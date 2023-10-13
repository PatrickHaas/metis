package io.metis.mitarbeiter.domain.mitarbeiter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EinstelltAmTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        Assertions.assertThatThrownBy(() -> new EinstelltAm(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("hired on must not be null");
    }

}
