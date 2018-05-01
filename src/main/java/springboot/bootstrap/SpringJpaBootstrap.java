package springboot.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import springboot.domain.Participant;
import springboot.repositories.ParticipantRepository;

import java.time.LocalDateTime;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ParticipantRepository participantRepository;

    private Logger log = Logger.getLogger(SpringJpaBootstrap.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadProducts();
    }

    private void loadProducts() {
        Participant experiment1 = new Participant();
        experiment1.setEmail("experiment1");

        participantRepository.save(experiment1);
        Participant experiment2 = new Participant();
        experiment2.setEmail("experiment2");

        participantRepository.save(experiment2);
        Participant experiment3 = new Participant();
        experiment3.setEmail("experiment3");
        participantRepository.save(experiment3);

        experiment1 = participantRepository.findOneByEmail("experiment1");
        experiment1.setSecret("Secret1");
        experiment1.setEmailVerified(Boolean.TRUE);
        participantRepository.save(experiment1);

        experiment2 = participantRepository.findOneByEmail("experiment2");
        experiment2.setSecret("Secret2");
        experiment2.setEmailVerified(Boolean.TRUE);
        participantRepository.save(experiment2);

        experiment3 = participantRepository.findOneByEmail("experiment3");
        experiment3.setSecret("Secret3");
        experiment3.setEmailVerified(Boolean.TRUE);
        participantRepository.save(experiment3);
    }
}
