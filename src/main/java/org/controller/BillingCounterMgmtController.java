package org.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingCounterMgmtController {

    @RequestMapping("/status")
    public String getStatus() {
        return "Billing Counter is operational.";
    }
}
