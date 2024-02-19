package com.codigo.mstorresmunoz.infraestructure.adapter;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.codigo.mstorresmunoz.domain.aggregates.constant.Constant;
import com.codigo.mstorresmunoz.domain.aggregates.dto.PersonaDTO;
import com.codigo.mstorresmunoz.domain.aggregates.request.RequestPersona;
import com.codigo.mstorresmunoz.domain.aggregates.response.ResponseReniec;
import com.codigo.mstorresmunoz.domain.ports.out.PersonaServiceOut;
import com.codigo.mstorresmunoz.infraestructure.entity.PersonaEntity;
import com.codigo.mstorresmunoz.infraestructure.entity.TipoDocumentoEntity;
import com.codigo.mstorresmunoz.infraestructure.entity.TipoPersonaEntity;
import com.codigo.mstorresmunoz.infraestructure.mapper.PersonaMapper;
import com.codigo.mstorresmunoz.infraestructure.repository.PersonaRepository;
import com.codigo.mstorresmunoz.infraestructure.repository.TipoDocumentoRepository;
import com.codigo.mstorresmunoz.infraestructure.repository.TipoPersonaRepository;
import com.codigo.mstorresmunoz.infraestructure.rest.client.ClientReniec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaAdapter implements PersonaServiceOut {

    private final PersonaRepository personaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final TipoPersonaRepository tipoPersonaRepository;
    private final PersonaMapper personaMapper;
    private final ClientReniec clientReniec;

    @Value("${token.api}")
    private String tokenApi;

    @Override
    public PersonaDTO createPersonaOut(RequestPersona requestPersona) {
        ResponseReniec datosReniec = getExecutionReniec(requestPersona.getNumDoc());
        Optional<PersonaEntity> existe = personaRepository.findByNumDoc(requestPersona.getNumDoc());
        if(existe.isEmpty()){
            personaRepository.save(getEntity(datosReniec,requestPersona));
            return personaMapper.mapToDTO(getEntity(datosReniec,requestPersona));
        }

       return null;
    }

    @Override
    public Optional<PersonaDTO> obtenerPersonaOut(String numDoc) {
       // return Optional.ofNullable(personaMapper.mapToDTO(personaRepository.findByNumDoc(numDoc).get()));
        Optional<PersonaEntity> personaOptional = personaRepository.findByNumDoc(numDoc);
        if (personaOptional.isPresent()) {
            return Optional.of(personaMapper.mapToDTO(personaOptional.get()));
        }
            return Optional.empty();
    }

    @Override
    public List<PersonaDTO> obtenerTodosPersonaOut() {
        List<PersonaDTO> personaDTOList = new ArrayList<>();
        List<PersonaEntity> personaEntityList = personaRepository.findByEstado(1);
        for(PersonaEntity persona: personaEntityList){
            PersonaDTO personaDTO = personaMapper.mapToDTO(persona);
            personaDTOList.add(personaDTO);
        }

        return personaDTOList;
    }


    @Override
    public PersonaDTO acutalizarPersonaOut(Long id, RequestPersona requestPersona) {
        Optional<PersonaEntity> entityDB = personaRepository.findById(id);
        if(entityDB.isPresent()){
            ResponseReniec respuestaReniec = getExecutionReniec(requestPersona.getNumDoc());
            personaRepository.save(getEntityUpdate(respuestaReniec,entityDB.get(), requestPersona));
            return personaMapper.mapToDTO(getEntityUpdate(respuestaReniec,entityDB.get(), requestPersona));
        }
        return null;
    }

    @Override
    public PersonaDTO deletePersonaOut(Long id) {
        Optional<PersonaEntity> entityDB = personaRepository.findById(id);
        if(entityDB.isPresent()){
            entityDB.get().setEstado(0);
            entityDB.get().setUsuaDelet(Constant.AUDIT_ADMIN);
            entityDB.get().setDateDelet(getTimestamp());
            personaRepository.save(entityDB.get());
            return personaMapper.mapToDTO(entityDB.get());
        }
        return null;
    }

    //Métodos internos

    public ResponseReniec getExecutionReniec(String numDoc){
        String authorization = "Bearer "+tokenApi;
        return clientReniec.getInfoReniec(numDoc, authorization);
    }

    private PersonaEntity getEntity(ResponseReniec reniec, RequestPersona requestPersona){
        TipoDocumentoEntity tipoDocumento = tipoDocumentoRepository.findByCodTipo(requestPersona.getTipoDoc());
        TipoPersonaEntity tipoPersona = tipoPersonaRepository.findByDescTipo(requestPersona.getTipoPer());
        PersonaEntity entity = new PersonaEntity();
        entity.setNumDoc(reniec.getNumeroDocumento());
        entity.setNombres(reniec.getNombres());
        entity.setApePat(reniec.getApellidoPaterno());
        entity.setApeMat(reniec.getApellidoMaterno());
        entity.setEstado(Constant.STATUS_ACTIVE);
        entity.setUsuaCrea(Constant.AUDIT_ADMIN);
        entity.setDateCreate(getTimestamp());
        entity.setTipoDocumento(tipoDocumento);
        entity.setTipoPersona(tipoPersona);
        return entity;
    }

    private Timestamp getTimestamp(){
        long currenTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currenTime);
        return timestamp;
    }
    private PersonaEntity getEntityUpdate(ResponseReniec reniec, PersonaEntity personaActualizar, RequestPersona requestPersona){
        TipoDocumentoEntity tipoDocumento = tipoDocumentoRepository.findByCodTipo(requestPersona.getTipoDoc());
        TipoPersonaEntity tipoPersona = tipoPersonaRepository.findByDescTipo(requestPersona.getTipoPer());
        personaActualizar.setNombres(reniec.getNombres());
        personaActualizar.setApePat(reniec.getApellidoPaterno());
        personaActualizar.setApeMat(reniec.getApellidoPaterno());
        personaActualizar.setTipoDocumento(tipoDocumento);
        personaActualizar.setTipoPersona(tipoPersona);
        personaActualizar.setUsuaModif(Constant.AUDIT_ADMIN);
        personaActualizar.setDateModif(getTimestamp());
        return personaActualizar;
    }
}
