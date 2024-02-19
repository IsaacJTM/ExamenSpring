package com.codigo.mstorresmunoz.infraestructure.repository;

import com.codigo.mstorresmunoz.infraestructure.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<PersonaEntity,Long> {

    List<PersonaEntity> findByEstado (Integer estado);
    Optional<PersonaEntity> findByNumDoc(String numDoc);
}
