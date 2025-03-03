package com.team1.etarcade.egg.controller;

import com.team1.etarcade.egg.dto.EggResponseDTO;
import com.team1.etarcade.egg.service.EggService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/eggs")
@RequiredArgsConstructor
public class EggController {

    private final EggService eggService;



    //알 습득
    @PostMapping("/{userId}")
    public ResponseEntity<EggResponseDTO> acquireEgg(@PathVariable Long userId) {
        EggResponseDTO response = eggService.acquireEgg(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping({"/{userId}"})
    public ResponseEntity<List<EggResponseDTO>> getAllEggs(@PathVariable Long userId) {
        List<EggResponseDTO> eggs = eggService.getAllEggs(userId);
        return ResponseEntity.ok(eggs);
    }





}