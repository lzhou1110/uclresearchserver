package springboot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import springboot.domain.Participant;
import springboot.repositories.ParticipantRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${setting.invitation.active_links}")
    private Integer numberOfActiveLinks;
    @Value("${setting.frontend.address}")
    private String frontendUrl;

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

    private Participant checkSecret(Participant participant, String secret) {
        if (participant != null && secret != null && participant.getSecret().equals(secret)) {
            return participant;
        } else {
            return null;
        }
    }

    @Override
    public Participant getParticipantByEmail(String email) {
        Participant participant = participantRepository.findOneByEmail(email);
        return participant;
    }

    @Override
    public Participant getParticipantByIdAndSecret(Integer id, String secret) {
        logger.debug("getParticipantByIdAndSecret called");
        Participant participant = participantRepository.findById(id).orElse(null);
        return checkSecret(participant, secret);
    }

    @Override
    public Participant getParticipantByEmailAndSecret(String email, String secret) {
        logger.debug("getParticipantByIdAndSecret called");
        Participant participant = participantRepository.findOneByEmail(email);
        return checkSecret(participant, secret);
    }

    @Override
    public Participant getParticipantByIdAndEmailAndSecret(Integer id, String email, String secret) {
        logger.debug("getParticipantByIdAndSecret called");
        Participant participant = participantRepository.findOneByIdAndEmail(id, email);
        return checkSecret(participant, secret);
    }

    @Override
    public List<String> getInvitationLinksByEmailAndSecret(Integer id, String secret) {
        logger.debug("getInvitationLinksByEmailAndSecret called");
        Participant participant = participantRepository.findById(id).orElse(null);
        participant = checkSecret(participant, secret);
        if (participant == null) {
            return null;
        }
        List<Participant> participantList = participantRepository.findByInvitedByIdAndEmailIsNull(participant.getId());
        while (participantList.size() < numberOfActiveLinks) {
            Participant linkedParticipant = new Participant();
            linkedParticipant.setInvitedById(participant.getId());
            participantRepository.save(linkedParticipant);
            participantList = participantRepository.findByInvitedByIdAndEmailIsNull(participant.getId());
        }
        return participantList.stream().map(o -> String.format("%sparticipate/%s/%s",frontendUrl, o.getId(), o.getSecret())).collect(Collectors.toList());
    }

    @Override
    public Integer getTotalParticipants() {
        return (int) participantRepository.count();
    }

    @Override
    public Integer getTotalLevel1Child(Integer id) {
        return (int) participantRepository.countParticipantsByInvitedByIdAndEmailIsNotNull(id);
    }

    @Override
    public Integer getTotalLevel2Child(Integer id) {
        List<Participant> children = participantRepository.findByInvitedById(id);
        return children.stream().mapToInt(o -> (int) participantRepository.countParticipantsByInvitedByIdAndEmailIsNotNull(o.getId())).sum();
    }
}
