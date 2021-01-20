package com.axsos.exambuilder.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class apiController {

    @RequestMapping("")
    public String rateShow() {

        return "noor";
    }


}
