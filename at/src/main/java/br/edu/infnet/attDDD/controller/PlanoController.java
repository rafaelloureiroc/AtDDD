package br.edu.infnet.attDDD.controller;

import br.edu.infnet.attDDD.service.PlanoService;
import br.edu.infnet.attDDD.service.dto.PlanoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plano")
public class PlanoController {

    @Autowired
    private PlanoService planoService;

    @PostMapping("/criar")
    public ResponseEntity<PlanoDto> criarPlano(@Valid @RequestBody PlanoDto planoDto) {
        PlanoDto plano = planoService.criarPlano(planoDto);
        return ResponseEntity.ok(plano);
    }
}