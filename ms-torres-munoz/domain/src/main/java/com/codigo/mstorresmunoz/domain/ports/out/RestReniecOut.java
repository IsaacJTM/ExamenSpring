package com.codigo.mstorresmunoz.domain.ports.out;

import com.codigo.mstorresmunoz.domain.aggregates.response.ResponseReniec;

public interface RestReniecOut {

    ResponseReniec getInfoReniec(String numDoc);
}
