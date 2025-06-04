package com.logger.enhancer.LoggerEnhancer.controller;

import com.logger.enhancer.LoggerEnhancer.dto.LoggerResult;
import com.logger.enhancer.LoggerEnhancer.service.LoggerEnhancerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/logEnchanceApi")
public class UploadController {

    private final LoggerEnhancerService enhancerService;

    public UploadController(LoggerEnhancerService enhancerService) {
        this.enhancerService = enhancerService;
    }

    @PostMapping("/uploadzip")
    public ResponseEntity<List<LoggerResult>> uploadZip(@RequestParam("file") MultipartFile file) {
        if (!file.getOriginalFilename().endsWith(".zip")) {
            return null;
            //return ResponseEntity.badRequest().body("Only .zip files are allowed.");
        }

        try {
            List<LoggerResult> analysis = enhancerService.processZip(file);
            return ResponseEntity.ok(analysis);
        } catch (Exception e) {
            return null;
            /*return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());*/
        }
    }
}

