package io.metis.employees.domain.employee;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HiredOnTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        Assertions.assertThatThrownBy(() -> new HiredOn(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("hired on must not be null");
    }

}
