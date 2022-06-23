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
     * Recibimos la llamada del postman con X confdiciones --> http://localhost:8080/persona/getData?pagina=0&num=2&created_date=2011-07-05&fechaCondicion=after&name=alex
     * Le pido que me muestre los 3 primeros registros (0,1,2) del 0 al 2. La fecha tiene que ser después (fechaCondicion=after) al created_date y el nombre tiene que ser alex
     * */

    @GetMapping("/getData")
    public List<Persona> getData(@RequestParam HashMap<String, String> condiciones, @RequestParam("pagina") int primer, @RequestParam("num") int ultimo){
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

