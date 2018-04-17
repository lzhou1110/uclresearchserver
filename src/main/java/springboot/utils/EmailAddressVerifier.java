package springboot.utils;

import springboot.domain.Participant;
import springboot.services.EmailService;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailAddressVerifier {
    public static void verifyEmail(String email) throws AddressException {
        InternetAddress emailAddress = new InternetAddress(email);
        emailAddress.validate();
    }

    public static void sendInvitation(EmailService emailService, Participant participant) {
        String uuid = participant.getSecret();
        String email = participant.getEmail();
        String text = String.format(
                "Please click on this link to confirm you own this email: http://localhost:61806/invite/%s/%s",
                email,
                uuid
        );
        String subject = "This is your invitation to participate in the UCL research!";
        String from = "liyi.zhou.17@ucl.ac.uk";

        emailService.sendSimpleMessage(
                from,
                email,
                subject,
                text);
    }
}
