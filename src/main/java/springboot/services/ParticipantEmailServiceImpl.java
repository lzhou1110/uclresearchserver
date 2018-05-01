package springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import springboot.domain.Participant;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class ParticipantEmailServiceImpl implements ParticipantEmailService {

    @Value("${setting.frontend.address}")
    private String frontendUrl;

    @Autowired
    private EmailService emailService;

    public void sendEmailConfirmationLink(Integer id, String secret, String email) {
        StringBuilder sb = new StringBuilder();
        sb.append("Please click on this link to confirm you own this email:\n");
        sb.append(String.format("%sconfirm/%s/%s/%s\n", frontendUrl, id, email, secret));
        sb.append("\n");
        sb.append("----------------------------\n");
        sb.append("\n");
        sb.append("By clicking the link you agree to the following terms and conditions:");
        sb.append("\n");
        sb.append("Under the Data Protection Act, we have a legal duty to protect any information we collect from users\n");
        sb.append("\n");
        sb.append("The privacy policy below relates to personal data collected in the course of registration\n");
        sb.append("\n");
        sb.append("PERSONALLY PROVIDED INFORMATION\n");
        sb.append("The personal data supplied at registration are held in a database owned by the University College London, and are only ever processed for research purpose and purposes explicitly agreed to by the end user, in accordance with the Data Protection Act 1998.\n");
        sb.append("\n");
        sb.append("Personal data are:\n");
        sb.append("email address\n");
        sb.append("date of registration\n");
        sb.append("agreement to any end user or special conditions\n");
        sb.append("details of any data accessed/downloaded\n");
        sb.append("internal user database unique id\n");
        sb.append("any username and id required for user authentication and validation\n");
        sb.append("details of any usage(s) registered\n");
        sb.append("\n");
        sb.append("The personal data are used for authentication and statistical purposes, and for the purposes of the management of the service (including registration and access to data). They are also used for other lawful purposes notified to the user where user(s) have been given the opportunity to withdraw consent.\n");
        sb.append("\n");
        sb.append("Personal data relating to access to a particular data collection (e.g. in the form of a data usage report) may only ever be passed to:\n");
        sb.append("University College London\n");
        sb.append("Copyright and other intellectual property rights owners of the data collection\n");
        sb.append("The funders of the data collection\n");
        sb.append("The funders of the data service provider\n");
        sb.append("US Army\n");
        sb.append("\n");

        String subject = "Confirm your email to participate in the UCL research";
        emailService.sendSimpleMessage(email, subject, sb.toString());
    }
}
