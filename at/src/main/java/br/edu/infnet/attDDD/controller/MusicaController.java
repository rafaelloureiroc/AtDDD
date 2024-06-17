package br.edu.infnet.attDDD.controller;

import br.edu.infnet.attDDD.service.MusicaService;
import br.edu.infnet.attDDD.service.dto.MusicaDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/musica")
@RequiredArgsConstructor
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

    @PostMapping("/criar")
    public ResponseEntity<MusicaDto> criarMusica(@Valid @RequestBody MusicaDto musicaDto) throws Exception {
        MusicaDto musica = musicaService.criarMusica(musicaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(musica);

    }
}