package springboot.services;


import springboot.domain.Participant;

public interface ParticipantService {
    Iterable<Participant> listAllParticipants();

    Participant getParticipantById(Integer id);

    Participant saveParticipant(Participant participant);

    void deleteParticipant(Integer id);

    Participant getParticipantByEmailAndUUID(String email, String uuid);

    Participant getParticipantByEmail(String email);
}
