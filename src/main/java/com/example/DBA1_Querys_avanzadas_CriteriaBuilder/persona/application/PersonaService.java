package com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.application;

import com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.domain.Persona;
import com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.infraestructure.dto.input.PersonaInputDTO;
import com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.infraestructure.dto.output.PersonaOutputDTO;
import com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.infraestructure.repository.PersonaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PersonaService {

    /////////////////////////////DBA1 BuilderCriteria/////////////////////////////

    /**
     * Creamos lass variables estaticas de equal, greather_than, less_than que con los valores de equal, after y before (respectivamente)
     * estas variables las compararemos con el fechaCondicion que recibimos en la petición del postman
     * y utilizando un swicth filtraremos por nombre, usuario, apellido o fecha.
     * */

    public final static String GREATER_THAN = "after";
    public final static String LESS_THAN = "before";
    public final static String EQUAL = "equal";
    @PersistenceContext
    private EntityManager entityManager;

    public List<Persona> getData(HashMap<String, String> condiciones, int primer, int ultimo){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Persona> query= cb.createQuery(Persona.class);

        Root<Persona> root = query.from(Persona.class);

        List<Predicate> predicates = new ArrayList<>();

        /**
         * Condiciones tiene 2 campos el primero "field" es el parametro y "value" es el valor del parametro
         * */
        condiciones.forEach((field,value) -> {
            switch (field) {
                case "user":
                    predicates.add(cb.equal (root.get(field), (String)value));
                    break;
                case "name":
                    predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
                    break;
                case "surname":
                    predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
                    break;
                case "created_date":
                    String fechaCondicion=(String) condiciones.get("fechaCondicion");
                    Date fecha = parseDate(value);
                    if (GREATER_THAN.equals(fechaCondicion)) {
                        predicates.add(cb.greaterThan(root.get(field), fecha));
                    } else if (LESS_THAN.equals(fechaCondicion)) {
                        predicates.add(cb.lessThan(root.get(field), fecha));
                    } else if (EQUAL.equals(fechaCondicion)) {
                        predicates.add(cb.equal(root.get(field), fecha));
                    }
                    break;
            }
        });
        query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));

        /**
         * Creamos esta lsita de personas y con las funciones de setMaxResult(), setFistResult(), le establecemos el número máximo de resultados y el primer resultado que ha de retornar.
         * */
        
        List<Persona> personaLista = entityManager.createQuery(query).setMaxResults(ultimo).setFirstResult(primer).getResultList();

        return personaLista;
    }

    /**
     * Este metodo me parsea de String a Date
     * */

    private Date parseDate(String fecha){
        Date fechaCreada;
        try{
            fechaCreada = new SimpleDateFormat("yyyy-mm-dd").parse(fecha);
        }catch(ParseException e){
            throw new RuntimeException(e);
        }
        return fechaCreada;
    }

    /////////////////////////////////////////////////////////////////////////

    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    ModelMapper modelMapper;

    public PersonaOutputDTO addPersona(PersonaInputDTO persona) throws Exception {
        if(persona.getUsuario() == null || persona.getPassword() == null || persona.getName() == null || persona.getCompany_email() == null
                || persona.getPersonal_email() == null || persona.getCity() == null || persona.getActive() == null
                || persona.getUsuario().length() > 10) throw new Exception("Los campos no se han establecido de manera correcta");

        Persona personaEntity = personaRepository.save(modelMapper.map(persona, Persona.class));
        return null;
    }

    public PersonaOutputDTO getPersonaByID(int id) throws Exception {
        Persona persona = personaRepository.findById(id).orElseThrow(()->new Exception("No se ha encontrado a la persona con el id: "+id));
        PersonaOutputDTO personaOutputDTO = modelMapper.map(persona, PersonaOutputDTO.class);
        return personaOutputDTO;
    }

    public List<PersonaOutputDTO> getPersonaByName(String name){

        List <PersonaOutputDTO> listaPersonasDTO = new ArrayList<>();
        personaRepository.findAll().forEach(
                persona -> {
                    if(persona.getName().equals(name)){
                        PersonaOutputDTO personaOnputDTO =modelMapper.map(persona, PersonaOutputDTO.class);
                        listaPersonasDTO.add(personaOnputDTO);
                    }
                }
        );

        return listaPersonasDTO;
    }

    public List<PersonaOutputDTO> getAll(){
        List <PersonaOutputDTO> listaPersonas = new ArrayList<>();
        personaRepository.findAll().forEach(
                persona -> {
                    PersonaOutputDTO personaOutputDTO = modelMapper.map(persona, PersonaOutputDTO.class);
                    listaPersonas.add(personaOutputDTO);
                }
        );
        return listaPersonas;
    }

    public PersonaOutputDTO updatePersona(int id, PersonaInputDTO personaInputDTO) {
        Optional<Persona> personaEntity = personaRepository.findById(id);
        if (personaEntity.isPresent()) {
            personaEntity.get().setUsuario(personaInputDTO.getUsuario());
            personaEntity.get().setPassword(personaInputDTO.getPassword());
            personaEntity.get().setName(personaInputDTO.getName());
            personaEntity.get().setSurname(personaInputDTO.getSurname());
            personaEntity.get().setCompany_email(personaInputDTO.getCompany_email());
            personaEntity.get().setPersonal_email(personaInputDTO.getPersonal_email());
            personaEntity.get().setCity(personaInputDTO.getCity());
            personaEntity.get().setImagen_url(personaInputDTO.getImagen_url());
            personaEntity.get().setActive(personaInputDTO.getActive());

            personaRepository.saveAndFlush(modelMapper.map(personaEntity, Persona.class));
            PersonaOutputDTO personaOutputDTO = modelMapper.map(personaEntity, PersonaOutputDTO.class);
            return personaOutputDTO;
        } else {
            return null;
        }
    }

    public String deletePersona(int id){
        personaRepository.deleteById(id);
        return "Persona eliminada";
    }
}
