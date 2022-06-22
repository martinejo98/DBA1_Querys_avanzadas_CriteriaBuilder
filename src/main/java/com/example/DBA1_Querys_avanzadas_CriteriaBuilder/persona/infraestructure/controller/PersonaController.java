package com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.infraestructure.controller;

import com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.application.PersonaService;
import com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.domain.Persona;
import com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.infraestructure.dto.input.PersonaInputDTO;
import com.example.DBA1_Querys_avanzadas_CriteriaBuilder.persona.infraestructure.dto.output.PersonaOutputDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/addPersona")
    public String addPersona(@RequestBody PersonaInputDTO personaInputDTO) throws Exception {
        personaService.addPersona(personaInputDTO);
        return "Persona añadida";
    }

    ////////////////////////BuilderCriteria////////////////////////


    /**
     * Recibimos la llamada del postman con X confdiciones --> http://localhost:8080/persona/getData?created_date=21-01-2018&fechaCondicion=equal&name=alex
     * (La fecha tiene que ser igual (fechaCondicion=equal) al created_date y el nombre tiene que ser alex)
     * */

    @GetMapping("/getData/{primer}/{ultimo}")
    public List<Persona> getData(@RequestParam HashMap<String, String> condiciones, @PathVariable int primer, @PathVariable int ultimo){
        return personaService.getData(condiciones, primer, ultimo);
    }

    ///////////////////////////////////////////////////////////////

    @GetMapping("/getPersonaID/{id}")
    public PersonaOutputDTO getPersonaByID(@PathVariable int id) throws Exception {
        return personaService.getPersonaByID(id);
    }


    @GetMapping("/getPersonaName/{name}")
    public List<PersonaOutputDTO> getPersonaByName(@PathVariable String name){
        return personaService.getPersonaByName(name);
    }

    @GetMapping("/getAll")
    public List <PersonaOutputDTO> getAll(){
        return personaService.getAll();
    }

    @PutMapping("/update/{id}")
    public String updatePersona(@RequestBody PersonaInputDTO personaInputDTO, @PathVariable int id){
        personaService.updatePersona(id, personaInputDTO);
        return "Persona actualziada";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePersona(@PathVariable int id){
        personaService.deletePersona(id);
        return "Persona eliminada";
    }

}

