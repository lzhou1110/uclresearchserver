package springboot.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springboot.domain.Participant;
import springboot.dto.AccountDTO;
import springboot.services.ParticipantEmailService;
import springboot.services.ParticipantService;
import springboot.utils.EmailAddressVerifier;

import javax.mail.internet.AddressException;

@RestController
@RequestMapping("/")
@CrossOrigin
public class FrontendController {
    @Value("${setting.system.email.count}")
    Integer numberOfSystemEmails;

    @Autowired
    ParticipantEmailService participantEmailService;

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

    @ApiOperation(value = "Verify email")
    @RequestMapping(value = "/verify/seed/{rootId}/{rootSecret}/{email}", method = RequestMethod.GET, produces = "application/json")
    public void verifySeedEmail(@PathVariable Integer rootId, @PathVariable String rootSecret, @PathVariable String email)
            throws BadEmailException, EmailAlreadyExistException, NoAccessException {
        try {
            Participant rootParticipant = participantService.getParticipantByIdAndSecret(rootId, rootSecret);
            if (rootParticipant == null) {
                throw new NoAccessException();
            }
            email = email.toLowerCase();
            EmailAddressVerifier.verifyEmail(email);
            Participant participant = new Participant();
            participant.setEmail(email);
            participant.setInvitedById(rootId);
            participant = participantService.saveParticipant(participant);
            participantEmailService.sendEmailConfirmationLink(participant.getId(), participant.getSecret(), participant.getEmail());
        } catch (AddressException e) {
            throw new BadEmailException();
        } catch (Exception e) {
            throw new EmailAlreadyExistException();
        }
    }

    @ApiOperation(value = "Verify email")
    @RequestMapping(value = "/verify/normal/{id}/{secret}/{email}", method = RequestMethod.GET, produces = "application/json")
    public void verifyNormalEmail(@PathVariable Integer id, @PathVariable String secret, @PathVariable String email)
            throws BadEmailException, EmailAlreadyExistException, NoAccessException {
        try {
            Participant participant = participantService.getParticipantByIdAndSecret(id, secret);
            if (participant == null || participant.getEmail() != null) {
                throw new NoAccessException();
            }
            email = email.toLowerCase();
            EmailAddressVerifier.verifyEmail(email);
            participant.setEmail(email);
            participant = participantService.saveParticipant(participant);
            participantEmailService.sendEmailConfirmationLink(participant.getId(), participant.getSecret(), participant.getEmail());
        } catch (AddressException e) {
            throw new BadEmailException();
        } catch (Exception e) {
            throw new EmailAlreadyExistException();
        }
    }

    @ApiOperation(value = "Confirm Email")
    @RequestMapping(value = "/account/{id}/{email}/{secret}", method = RequestMethod.GET, produces = "application/json")
    public AccountDTO fetchAccount(@PathVariable Integer id, @PathVariable String email, @PathVariable String secret) throws NoAccessException, AlreadyInvitedException {
        Participant participant = participantService.getParticipantByIdAndEmailAndSecret(id, email, secret);
        if (participant == null) {
            throw new NoAccessException();
        }
        if (!participant.getEmailVerified()) {
            participant.setEmailVerified(Boolean.TRUE);
            participant = participantService.saveParticipant(participant);
        }
        AccountDTO accountDto = new AccountDTO(participant);
        accountDto.setTotalParticipants(participantService.getTotalParticipants() - numberOfSystemEmails);
        accountDto.setTotalLevel1Children(participantService.getTotalLevel1Child(id));
        accountDto.setTotalLevel2Children(participantService.getTotalLevel2Child(id));
        accountDto.setScore(accountDto.getTotalLevel1Children() + accountDto.getTotalLevel2Children());
        return accountDto;
    }


}
