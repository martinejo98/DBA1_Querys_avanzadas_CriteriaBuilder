package com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.infraestructure.dto.output;

import com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.domain.Persona;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PersonaOutputDTO {

    private int id_persona;
    private String usuario;
    private String name;
    private String surname;
    private String company_email;
    private String personal_email;
    private String city;
    private Boolean active;
    private Date created_date;
    private String imagen_url;
    private Date termination_date;

    public PersonaOutputDTO(Persona persona){
        setId_persona(persona.getId_persona());
        setUsuario(persona.getUsuario());
        setName(persona.getName());
        setSurname(persona.getSurname());
        setCompany_email(persona.getCompany_email());
        setPersonal_email(persona.getPersonal_email());
        setCity(persona.getCity());
        setActive(persona.getActive());
        setCreated_date(persona.getCreated_date());
        setImagen_url(persona.getImagen_url());
        setTermination_date(persona.getTermination_date());
    }
}
