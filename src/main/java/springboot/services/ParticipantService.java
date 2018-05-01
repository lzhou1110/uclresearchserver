package springboot.services;

import springboot.domain.Participant;

import java.util.List;

public interface ParticipantService {
    Iterable<Participant> listAllParticipants();

    Participant getParticipantById(Integer id);

    Participant saveParticipant(Participant participant);

    void deleteParticipant(Integer id);

    Participant getParticipantByEmail(String email);

    Participant getParticipantByIdAndSecret(Integer id, String secret);

    Participant getParticipantByEmailAndSecret(String email, String secret);

    Participant getParticipantByIdAndEmailAndSecret(Integer id, String email, String secret);

    List<String> getInvitationLinksByEmailAndSecret(Integer id, String secret);

    Integer getTotalParticipants();

    Integer getTotalLevel1Child(Integer id);

    Integer getTotalLevel2Child(Integer id);
}
