package com.admin.ui.config;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomNotifier extends AbstractEventNotifier {

    private final Logger logger = LoggerFactory.getLogger(CustomNotifier.class);

    protected CustomNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {

        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                logger.info("Instance {} ({}) is {}", instance.getRegistration().getName(), event.getInstance(),
                        ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());

                InstanceStatusChangedEvent instanceStatusChangedEvent = (InstanceStatusChangedEvent)event;

                StatusInfo statusInfo = instanceStatusChangedEvent.getStatusInfo();
                if(statusInfo.isDown() || statusInfo.isOffline() || statusInfo.isUnknown()){
                    logger.info("Application {}", instanceStatusChangedEvent.getStatusInfo());
                    String text = "改变了" + instanceStatusChangedEvent.getStatusInfo();
                    logger.warn(text);
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo("*****@*****");
                    message.setFrom("*****@*****");
                    message.setSubject("服务状态改变");
                    message.setText(text);
                    javaMailSender.send(message);
                }

            }else {
                logger.info("Instance {} ({}) {}", instance.getRegistration().getName(), event.getInstance(),
                        event.getType());
            }
        });
    }
}
