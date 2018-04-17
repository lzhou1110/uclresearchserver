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
        Participant participant1 = new Participant();
        participant1.setFriendEmails(new String[] {"friend1@email.com", "friend2@email.com"});
        participant1.setEmail("participant1@email.com");
        participantRepository.save(participant1);
        log.info(String.format("Saved participant with id {}", participant1.getId()));

        Participant participant2 = new Participant();
        participant2.setFriendEmails(new String[] {"friend3@email.com", "friend4@email.com"});
        participant2.setEmail("email@gmail.com");
        participantRepository.save(participant2);
        log.info(String.format("Saved participant with id {}", participant2.getId()));
    }
}
