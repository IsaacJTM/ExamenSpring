package com.codigo.mstorresmunoz.infraestructure.repository;

import com.codigo.mstorresmunoz.infraestructure.entity.TipoPersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface TipoPersonaRepository extends JpaRepository<TipoPersonaEntity,Long> {

    TipoPersonaEntity findByDescTipo (@Param("descTipo") String descTipo);

}
