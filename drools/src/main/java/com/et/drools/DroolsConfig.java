package com.et.drools;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class DroolsConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroolsConfig.class);

    private KieServices kieServices = KieServices.Factory.get();

    @Bean
    public KieSession getKieSession() throws IOException {
        LOGGER.info("Session created...");
        KieSession kieSession = getKieContainer().newKieSession();
        kieSession.setGlobal("showResults", new OutputDisplay());
        kieSession.setGlobal("sh", new OutputDisplay());

        kieSession.addEventListener(new RuleRuntimeEventListener() {
            @Override
            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println("Object inserted \n "
                        + event.getObject().toString());
            }

            @Override
            public void objectUpdated(ObjectUpdatedEvent event) {
                System.out.println("Object was updated \n"
                        + "New Content \n"
                        + event.getObject().toString());
            }

            @Override
            public void objectDeleted(ObjectDeletedEvent event) {
                System.out.println("Object retracted \n"
                        + event.getOldObject().toString());
            }
        });
        return kieSession;
    }

    @Bean
    public KieContainer getKieContainer() throws IOException {
        LOGGER.info("Container created...");
        getKieRepository();
        KieBuilder kb = kieServices.newKieBuilder(getKieFileSystem());
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    private void getKieRepository() {
        final KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(new KieModule() {
            @Override
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });
    }

    private KieFileSystem getKieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("order.drl"));
        return kieFileSystem;
    }
}
