package com.codigo.mstorresmunoz.infraestructure.mapper;

import com.codigo.mstorresmunoz.domain.aggregates.dto.PersonaDTO;
import com.codigo.mstorresmunoz.infraestructure.entity.PersonaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class PersonaMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public PersonaDTO mapToDTO (PersonaEntity entity){
        return modelMapper.map(entity, PersonaDTO.class);
    }

    public PersonaEntity mapToEntity(PersonaDTO personaDTO){
        return modelMapper.map(personaDTO,PersonaEntity.class);
    }
}
