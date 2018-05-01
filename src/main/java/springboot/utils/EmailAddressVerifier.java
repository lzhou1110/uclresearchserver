package springboot.utils;

import org.springframework.beans.factory.annotation.Value;
import springboot.domain.Participant;
import springboot.services.EmailService;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailAddressVerifier {
    public static void verifyEmail(String email) throws AddressException {
        InternetAddress emailAddress = new InternetAddress(email);
        emailAddress.validate();
    }
}
