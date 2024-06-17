package br.edu.infnet.attDDD.controller;

import br.edu.infnet.attDDD.domain.Musica;
import br.edu.infnet.attDDD.domain.Playlist;
import br.edu.infnet.attDDD.service.UsuarioService;
import br.edu.infnet.attDDD.service.dto.UsuarioDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity<UsuarioDto> criar(@RequestBody @Valid UsuarioDto dto) throws Exception {
        UsuarioDto response = this.usuarioService.criar(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/favoritar")
    public ResponseEntity<String> favoritarMusica(@RequestBody Map<String, Object> request) {
        try {
            UUID idUsuario = UUID.fromString((String) request.get("idUsuario"));
            UUID idPlaylist = UUID.fromString((String) request.get("idPlaylist"));
            UUID idMusica = UUID.fromString((String) request.get("idMusica"));

            boolean success = usuarioService.favoritarMusica(idUsuario, idPlaylist, idMusica);
            if (success) {
                return ResponseEntity.ok("Música favoritada com sucesso.");
            } else {
                return ResponseEntity.badRequest().body("Falha ao favoritar música.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar a solicitação.");
        }
    }

    @GetMapping("/{idUsuario}/playlists")
    public ResponseEntity<List<Playlist>> listarPlaylists(@PathVariable UUID idUsuario) {
        try {
            List<Playlist> playlists = usuarioService.listarPlaylists(idUsuario);
            if (playlists != null) {
                return ResponseEntity.ok(playlists);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
