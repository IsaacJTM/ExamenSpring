package com.codigo.mstorresmunoz.infraestructure.repository;

import com.codigo.mstorresmunoz.infraestructure.entity.TipoDocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoEntity,Long> {
    TipoDocumentoEntity findByCodTipo(@Param("codTipo") String codTipo);
}
