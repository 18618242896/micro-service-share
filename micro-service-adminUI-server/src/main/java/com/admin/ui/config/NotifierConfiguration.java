package com.admin.ui.config;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
public class NotifierConfiguration {

    private static final Logger log = LoggerFactory.getLogger(NotifierConfiguration.class);

    @Autowired
    private CustomNotifier notifier;
    @Autowired
    private InstanceRepository repository;

    private String[] reminderStatuses = { "DOWN" };

    @Bean
    @Primary
    public RemindingNotifier remindingNotifier() {

        RemindingNotifier remindingNotifier = new RemindingNotifier(notifier,repository);
        remindingNotifier.setReminderStatuses(reminderStatuses);
        //remindingNotifier.setReminderPeriod(TimeUnit.MINUTES.toMillis(5));
        return remindingNotifier;
    }
}
