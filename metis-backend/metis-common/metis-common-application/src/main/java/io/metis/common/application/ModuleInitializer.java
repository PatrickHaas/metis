package io.metis.common.application;

import java.util.Collections;
import java.util.Set;

public interface ModuleInitializer {

    record Configuration(boolean shouldCreateSampleData) {}

    void initialize(Configuration configuration);

    default Set<Class<? extends ModuleInitializer>> getDependencies() {
        return Collections.emptySet();
    }

}
