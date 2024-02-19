package com.codigo.mstorresmunoz.domain.impl;

import com.codigo.mstorresmunoz.domain.aggregates.dto.PersonaDTO;
import com.codigo.mstorresmunoz.domain.aggregates.request.RequestPersona;
import com.codigo.mstorresmunoz.domain.ports.in.PersonaServiceIn;
import com.codigo.mstorresmunoz.domain.ports.out.PersonaServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaServiceIn {

    private final PersonaServiceOut personaServiceOut;
    @Override
    public PersonaDTO createPersonaIn(RequestPersona requestPersona) {
        return personaServiceOut.createPersonaOut(requestPersona);
    }

    @Override
    public Optional<PersonaDTO> obtenerPersonaIn(String numDoc) {
        return personaServiceOut.obtenerPersonaOut(numDoc);
    }

    @Override
    public List<PersonaDTO> obtenerTodosPersonaIn() {
        return personaServiceOut.obtenerTodosPersonaOut();
    }

    @Override
    public PersonaDTO acutalizarPersonaIn(Long id, RequestPersona requestPersona) {
        return personaServiceOut.acutalizarPersonaOut(id, requestPersona);
    }

    @Override
    public PersonaDTO deletePersonaIn(Long id) {
        return personaServiceOut.deletePersonaOut(id);
    }
}
