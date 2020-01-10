package org.zisecm.extinterface.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class DataOperatorController {
	@GetMapping("/greeting")
    public String greeting() {
        return "Hello,World!";
    }
}
