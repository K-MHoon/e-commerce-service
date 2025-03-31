package com.kmhoon.app.controllers;

import com.kmhoon.app.PaymentApi;
import com.kmhoon.app.hateoas.PaymentRepresentationModelAssembler;
import com.kmhoon.app.model.Authorization;
import com.kmhoon.app.model.PaymentReq;
import com.kmhoon.app.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {

    private final PaymentService paymentService;
    private final PaymentRepresentationModelAssembler assembler;


//    @Override
//    public ResponseEntity<Authorization> authorize(PaymentReq paymentReq) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<Authorization> getOrdersPaymentAuthorization(String orderId) {
//        return null;
//    }
}
