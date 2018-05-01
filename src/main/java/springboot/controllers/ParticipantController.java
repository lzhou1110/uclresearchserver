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
import springboot.services.ParticipantEmailService;
import springboot.services.ParticipantService;

@RestController
@RequestMapping("/participant")
@Api(value = "participant", description = "participant api")
@CrossOrigin
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @ApiOperation(value = "View a list of available participants", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public Iterable<Participant> list(Model model) {
        Iterable<Participant> participantList = participantService.listAllParticipants();
        return participantList;
    }

    @ApiOperation(value = "Search a participant with an ID", response = Participant.class)
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET, produces = "application/json")
    public Participant showParticipants(@PathVariable Integer id, Model model) {
        Participant participant = participantService.getParticipantById(id);
        return participant;
    }

    @ApiOperation(value = "Add a product")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity saveParticipant(@RequestBody Participant participant) {
        participantService.saveParticipant(participant);
        return new ResponseEntity("Participant saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update a participant using id and uuid")
    @RequestMapping(value = "/update/{id}/secret/{secret}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateParticipantUsingIdAndSecret(@PathVariable Integer id, @PathVariable String secret, @RequestBody Participant participant) {
        Participant storedParticipant = participantService.getParticipantByIdAndSecret(id, secret);
        storedParticipant.setEmail(participant.getEmail());
        participantService.saveParticipant(storedParticipant);
        return new ResponseEntity("Product updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Generate invitation entities for a participate")
    @RequestMapping(value = "/invitations/{id}/secret/{secret}", method = RequestMethod.GET, produces = "application/json")
    public Iterable<String> getInvitationLinks(@PathVariable Integer id, @PathVariable String secret) {
        Iterable<String> participantList = participantService.getInvitationLinksByEmailAndSecret(id, secret);
        return participantList;
    }

    @ApiOperation(value = "Delete a participant")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity delete(@PathVariable Integer id) {
        participantService.deleteParticipant(id);
        return new ResponseEntity("Product deleted successfully", HttpStatus.OK);
    }
}