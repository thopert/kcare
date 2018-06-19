package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Person;

import at.qe.sepm.skeleton.repositories.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;


/**
 * Service for handling with people.
 * @author Dominik Kuen (csat2284)
 */
@Component
@Scope("application")
    public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERVISOR')")
    public Person loadPerson(Long id){
        return this.personRepository.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void savePerson(Person person){
        this.personRepository.save(person);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deletePerson(Person person){
        this.personRepository.delete(person);
    }

}
