package springboot.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springboot.domain.Participant;
import springboot.services.EmailService;
import springboot.services.ParticipantService;
import springboot.utils.EmailAddressVerifier;

import javax.mail.internet.AddressException;

@RestController
@RequestMapping("/")
@CrossOrigin
public class VerifyController {
    @Autowired
    EmailService emailService;

    @Autowired
    ParticipantService participantService;

    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Bad Email Address")
    public class BadEmailException extends Exception {
        public BadEmailException() {
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Email already registered")
    public class EmailAlreadyExistException extends Exception {
        public EmailAlreadyExistException() {
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Does not have access")
    public class NoAccessException extends Exception {
        public NoAccessException() {
        }
    }

    @ResponseStatus(code = HttpStatus.ALREADY_REPORTED, reason = "Does not have access")
    public class AlreadyInvitedException extends Exception {
        public AlreadyInvitedException() {
        }
    }

    @ApiOperation(value = "Verify Email")
    @RequestMapping(value = "/verify/{email}", method = RequestMethod.GET, produces = "application/json")
    public void verifyEmail(@PathVariable String email) throws BadEmailException, EmailAlreadyExistException {
        try {
            email = email.toLowerCase();
            EmailAddressVerifier.verifyEmail(email);
            Participant participant = new Participant();
            participant.setEmail(email);
            participant = participantService.saveParticipant(participant);
            EmailAddressVerifier.sendInvitation(emailService, participant);
        } catch (AddressException e) {
            throw new BadEmailException();
        } catch (Exception e) {
            throw new EmailAlreadyExistException();
        }
    }

    @ApiOperation(value = "Confirm Email")
    @RequestMapping(value = "/confirm/{email}/{uuid}", method = RequestMethod.GET, produces = "application/json")
    public void confirmEmail(@PathVariable String email, @PathVariable String uuid) throws NoAccessException, AlreadyInvitedException {
        Participant participant = participantService.getParticipantByEmailAndUUID(email, uuid);
        if (participant == null) {
            throw new NoAccessException();
        }
        if (participant.getFriendEmails() != null) {
            throw new AlreadyInvitedException();
        }
    }
}
