package com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //CREO UN ID AUTOGENERADO
    int id_persona;

    @Column
    private String usuario;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String company_email;

    @Column
    private String personal_email;

    @Column
    private String city;

    @Column
    private Boolean active;

    @Column
    private Date created_date;

    @Column
    private String imagen_url;

    @Column
    private Date termination_date;

}
