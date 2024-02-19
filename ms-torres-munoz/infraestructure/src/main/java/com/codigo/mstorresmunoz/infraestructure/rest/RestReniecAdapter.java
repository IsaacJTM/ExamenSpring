package com.codigo.mstorresmunoz.infraestructure.rest;


import com.codigo.mstorresmunoz.domain.aggregates.response.ResponseReniec;
import com.codigo.mstorresmunoz.domain.ports.out.RestReniecOut;
import com.codigo.mstorresmunoz.infraestructure.rest.client.ClientReniec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestReniecAdapter implements RestReniecOut {

    private final ClientReniec clientReniec;

    @Value("${token.api}")
    private String tokenApi;

    @Override
    public ResponseReniec getInfoReniec(String numDoc) {
        String authorization = "Bearer "+ tokenApi;
        return clientReniec.getInfoReniec(numDoc, authorization);
    }
}
