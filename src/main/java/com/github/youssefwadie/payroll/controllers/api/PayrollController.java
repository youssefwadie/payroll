package com.github.youssefwadie.payroll.controllers.api;

import com.github.youssefwadie.payroll.entities.PayPeriod;
import com.github.youssefwadie.payroll.reports.PayrollReport;
import com.github.youssefwadie.payroll.services.PayrollService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/v1/payroll", produces = "application/json")
@AllArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @GetMapping(consumes = "application/json")
    public ResponseEntity<PayrollReport> getReport(@RequestBody PayPeriod payPeriod)
            throws InterruptedException, ExecutionException {

//        if (!computation.isDone()) {
        // TODO: handle long wait time
        //  1 - throw exception
        //  2 - wait for it to be done
//        }

        CompletableFuture<PayrollReport> computation =
                payrollService.payroll(payPeriod.getStartDate(), payPeriod.getEndDate());
        // for now just wait for whatever it takes to be done
        return new ResponseEntity<>(computation.get(), HttpStatus.OK);
    }

}
