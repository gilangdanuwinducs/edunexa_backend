package com.sekolah.project_sekolah.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CorsOptionsController {

    @RequestMapping(method = RequestMethod.OPTIONS, path = "/**")
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }
}
