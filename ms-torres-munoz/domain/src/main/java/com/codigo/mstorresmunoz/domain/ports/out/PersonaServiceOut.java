package com.codigo.mstorresmunoz.domain.ports.out;

import com.codigo.mstorresmunoz.domain.aggregates.dto.PersonaDTO;
import com.codigo.mstorresmunoz.domain.aggregates.request.RequestPersona;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceOut {

    PersonaDTO createPersonaOut (RequestPersona requestPersona);
    Optional<PersonaDTO> obtenerPersonaOut(String numDoc);
    List<PersonaDTO> obtenerTodosPersonaOut();
    PersonaDTO acutalizarPersonaOut(Long id, RequestPersona requestPersona);
    PersonaDTO deletePersonaOut(Long id);
}
