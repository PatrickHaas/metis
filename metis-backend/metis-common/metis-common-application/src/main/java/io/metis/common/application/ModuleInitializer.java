package io.metis.common.application;

import java.util.Collections;
import java.util.Set;

public interface ModuleInitializer {

    void initialize();

    default Set<Class<? extends ModuleInitializer>> getDependencies() {
        return Collections.emptySet();
    }

}
