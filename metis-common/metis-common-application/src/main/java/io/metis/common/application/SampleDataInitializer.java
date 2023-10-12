package io.metis.common.application;

import java.util.Collections;
import java.util.Set;

public interface SampleDataInitializer {

    void initialize();

    default Set<Class<? extends SampleDataInitializer>> getDependencies() {
        return Collections.emptySet();
    }

}
