package com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.infraestructure.repository;

import com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository <Persona, Integer> {
    //public List<Persona> getData(HashMap<String, Object> conditions);
}

