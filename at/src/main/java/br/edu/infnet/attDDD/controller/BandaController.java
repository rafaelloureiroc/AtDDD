package br.edu.infnet.attDDD.controller;

import br.edu.infnet.attDDD.service.BandaService;
import br.edu.infnet.attDDD.service.dto.BandaDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banda")
public class BandaController {

    @Autowired
    private BandaService bandaService;

    @PostMapping("/criar")
    public ResponseEntity<BandaDto> criarBanda(@Valid @RequestBody BandaDto bandaDTO) {
        BandaDto bandaCriada = bandaService.criarBanda(bandaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bandaCriada);
    }

}