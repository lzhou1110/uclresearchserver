package springboot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.domain.Participant;
import springboot.repositories.ParticipantRepository;

@Service
public class ParticipantServiceImpl implements ParticipantService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    public Iterable<Participant> listAllParticipants() {
        logger.debug("ListingAllParticipants called");
        return participantRepository.findAll();
    }

    @Override
    public Participant getParticipantById(Integer id) {
        logger.debug("getParticipantById called");
        return participantRepository.findById(id).orElse(null);
    }

    @Override
    public Participant saveParticipant(Participant participant) {
        logger.debug("saveParticipant called");
        return participantRepository.save(participant);
    }

    @Override
    public void deleteParticipant(Integer id) {
        logger.debug("deleteParticipant called");
        participantRepository.deleteById(id);
    }

    @Override
    public Participant getParticipantByEmailAndUUID(String email, String uuid) {
        logger.debug("finding participant by email and uuid called");
        Participant participant = participantRepository.findOneByEmail(email);
        if (participant != null && participant.getSecret() != null) {
            if (participant.getSecret().equals(uuid)) {
                return participant;
            }
        }
        return null;
    }

    @Override
    public Participant getParticipantByEmail(String email) {
        Participant participant = participantRepository.findOneByEmail(email);
        return participant;
    }
}
