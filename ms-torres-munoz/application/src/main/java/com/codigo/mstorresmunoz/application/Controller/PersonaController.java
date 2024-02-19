package com.codigo.mstorresmunoz.application.Controller;

import com.codigo.mstorresmunoz.domain.aggregates.dto.PersonaDTO;
import com.codigo.mstorresmunoz.domain.aggregates.request.RequestPersona;
import com.codigo.mstorresmunoz.domain.ports.in.PersonaServiceIn;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@OpenAPIDefinition(
        info = @Info(
                title = "API-PERSONA",
                version = "1.0",
                description = "Proyecto de Persona"
        )
)

@RestController
@RequestMapping("v2/persona")
@RequiredArgsConstructor
public class PersonaController {
    private final PersonaServiceIn personaServiceIn;

    @Operation(summary = "Crear una Persona")
    @PostMapping
    public ResponseEntity <?> savePersona(@RequestBody RequestPersona requestPersona){
        PersonaDTO personaDTO = personaServiceIn.createPersonaIn(requestPersona);
        if(personaDTO != null){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(personaDTO);
        }
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("El registro ya existe");
    }

    @Operation(summary = "Buscar a una persona")
    @GetMapping("/{numDoc}")
    public ResponseEntity <?> buscarPersona(@PathVariable String numDoc){
        Optional<PersonaDTO> personaOptional = personaServiceIn.obtenerPersonaIn(numDoc);
        if (personaOptional.isPresent()) {
            return ResponseEntity.ok(personaOptional.get());
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro no encontrado");
    }

    @Operation(summary = "Listar todas las personas")
    @GetMapping()
    public ResponseEntity<List<PersonaDTO>> ObtenerListadoPersona(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.obtenerTodosPersonaIn());


    }

    @Operation (summary = "Actuliazar a una persona")
    @GetMapping("actualizar/{id}")
    public ResponseEntity<PersonaDTO> actualizarPersona(@PathVariable Long id, @RequestBody RequestPersona requestPersona){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.acutalizarPersonaIn(id,requestPersona));
    }

    @Operation (summary = "Eliminar a una persona")
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<PersonaDTO> elimanarPersona(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.deletePersonaIn(id));
    }


}
