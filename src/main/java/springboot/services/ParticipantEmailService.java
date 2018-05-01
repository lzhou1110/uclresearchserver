package springboot.services;

import springboot.domain.Participant;

public interface ParticipantEmailService {
    public void sendEmailConfirmationLink(Integer id, String secret, String email);
}
