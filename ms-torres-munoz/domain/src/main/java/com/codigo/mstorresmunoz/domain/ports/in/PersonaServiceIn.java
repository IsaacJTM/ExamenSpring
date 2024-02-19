package com.codigo.mstorresmunoz.domain.ports.in;

import com.codigo.mstorresmunoz.domain.aggregates.dto.PersonaDTO;
import com.codigo.mstorresmunoz.domain.aggregates.request.RequestPersona;
import java.util.List;
import java.util.Optional;

public interface PersonaServiceIn {

    PersonaDTO createPersonaIn (RequestPersona requestPersona);
    Optional<PersonaDTO> obtenerPersonaIn(String numDoc);
    List<PersonaDTO> obtenerTodosPersonaIn();
    PersonaDTO acutalizarPersonaIn(Long id, RequestPersona requestPersona);
    PersonaDTO deletePersonaIn(Long id);


}
