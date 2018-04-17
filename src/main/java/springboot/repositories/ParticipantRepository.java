package springboot.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import springboot.domain.Participant;

import java.util.List;

@RepositoryRestResource
public interface ParticipantRepository extends CrudRepository<Participant, Integer>{
    Participant findOneByEmail(String email);
}
