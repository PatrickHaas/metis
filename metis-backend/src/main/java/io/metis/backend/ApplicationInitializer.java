package io.metis.backend;

import io.metis.common.application.ModuleInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Slf4j
@Component
@ConditionalOnProperty(value = "modules.initialize", havingValue = "true")
@RequiredArgsConstructor
public class ApplicationInitializer implements ApplicationRunner {

    private final ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) {
        Reflections reflections = new Reflections("io.metis");
        Set<Class<? extends ModuleInitializer>> moduleInitializers = reflections.getSubTypesOf(ModuleInitializer.class);
        log.debug("found module initializers: {}", moduleInitializers);

        Set<ModuleInitializer> moduleInitializerBeans = new HashSet<>();

        for (Class<? extends ModuleInitializer> aClass : moduleInitializers) {
            ModuleInitializer moduleInitializerBean = applicationContext.getAutowireCapableBeanFactory().createBean(aClass);
            log.debug("created module initializer: {}", moduleInitializerBean);
            moduleInitializerBeans.add(moduleInitializerBean);
        }

        // TODO I guess there's a better solution
        Queue<ModuleInitializer> initializationQueue = new LinkedList<>();
        while (!moduleInitializerBeans.isEmpty()) {
            initializationQueue.addAll(moduleInitializerBeans.stream().filter(moduleInitializer -> moduleInitializer.getDependencies().isEmpty() || new HashSet<>(initializationQueue.stream().map(Object::getClass).toList()).containsAll(moduleInitializer.getDependencies())).toList());
            moduleInitializerBeans.removeAll(initializationQueue);
        }
        log.debug("create module initializer dependency tree");

        for (ModuleInitializer moduleInitializer : initializationQueue) {
            long start = System.currentTimeMillis();
            moduleInitializer.initialize();
            long duration = System.currentTimeMillis() - start;
            log.debug("executed module initializer {} in {}ms", moduleInitializer, duration);
        }
    }
}
