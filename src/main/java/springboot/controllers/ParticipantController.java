package springboot.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.domain.Participant;
import springboot.services.EmailService;
import springboot.services.ParticipantService;
import springboot.utils.EmailAddressVerifier;

@RestController
@RequestMapping("/participant")
@Api(value="participant", description="participant api")
@CrossOrigin
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "View a list of available participants",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/list", method= RequestMethod.GET, produces = "application/json")
    public Iterable<Participant> list(Model model){
        Iterable<Participant> participantList = participantService.listAllParticipants();
        return participantList;
    }
    @ApiOperation(value = "Search a participant with an ID",response = Participant.class)
    @RequestMapping(value = "/show/{id}", method= RequestMethod.GET, produces = "application/json")
    public Participant showProduct(@PathVariable Integer id, Model model){
        Participant participant = participantService.getParticipantById(id);
        return participant;
    }

    @ApiOperation(value = "Add a product")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity saveProduct(@RequestBody Participant participant){
        participantService.saveParticipant(participant);
        return new ResponseEntity("Participant saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update a participant")
    @RequestMapping(value = "/update/{email}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateProduct(@PathVariable String email, @RequestBody Participant participant){
        Participant storedParticipant = participantService.getParticipantByEmail(email);
        storedParticipant.setFriendEmails(participant.getFriendEmails());
        participantService.saveParticipant(storedParticipant);

        for (String emailAddress : participant.getFriendEmails()) {
            try {
                Participant newParticipant = new Participant();
                newParticipant.setEmail(emailAddress);
                participantService.saveParticipant(newParticipant);
                email = email.toLowerCase();
                EmailAddressVerifier.verifyEmail(email);
                participant = participantService.saveParticipant(newParticipant);
                EmailAddressVerifier.sendInvitation(emailService, newParticipant);
            } catch (Exception ignored) {
            }
        }


        return new ResponseEntity("Product updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a product")
    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity delete(@PathVariable Integer id){
        participantService.deleteParticipant(id);
        return new ResponseEntity("Product deleted successfully", HttpStatus.OK);
    }
}
