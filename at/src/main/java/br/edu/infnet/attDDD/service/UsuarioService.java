package br.edu.infnet.attDDD.service;


import br.edu.infnet.attDDD.domain.*;
import br.edu.infnet.attDDD.repository.MusicaRepository;
import br.edu.infnet.attDDD.repository.PlanoRepository;
import br.edu.infnet.attDDD.repository.UsuarioRepository;
import br.edu.infnet.attDDD.service.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    public UsuarioDto criar(UsuarioDto dto) throws Exception {
        // Verificar se temos um plano válido
        Optional<Plano> plano = this.planoRepository.findById(dto.getIdPlano());

        if (plano.isEmpty()) {
            throw new Exception("Plano não encontrado");
        }

        // Criando um objeto de cartão
        Cartao cartao = new Cartao();
        cartao.setNumero(dto.getNumeroCartao());
        cartao.setAtivo(dto.getCartaoAtivo());
        cartao.setLimite(dto.getLimite());

        // Verificar se o CPF está cadastrado
        if (this.usuarioRepository.findUsuarioByCpf(dto.getCpf()).isPresent()) {
            throw new Exception("CPF do usuário já cadastrado");
        }

        // Criar o usuário
        Usuario usuario = new Usuario();
        usuario.criar(dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getCpf(), cartao, plano.get());

        usuario = this.usuarioRepository.save(usuario);

        // Criar a resposta para o controller
        UsuarioDto response = new UsuarioDto();
        response.setId(usuario.getId());
        response.setEmail(usuario.getEmail());
        response.setNome(usuario.getNome());
        response.setSenha("************");
        response.setNumeroCartao("***** ***** ***** *****");
        response.setCartaoAtivo(cartao.getAtivo());
        response.setLimite(cartao.getLimite());
        response.setIdPlano(plano.get().getId());
        response.setCpf(usuario.getCpf());

        return response;
    }

    public boolean favoritarMusica(UUID idUsuario, UUID idPlaylist, UUID idMusica) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        Optional<Musica> musicaOpt = musicaRepository.findById(idMusica);

        if (usuarioOpt.isPresent() && musicaOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Musica musica = musicaOpt.get();
            usuario.favoritarMusica(idPlaylist, musica);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public List<Playlist> listarPlaylists(UUID idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (usuarioOpt.isPresent()) {
            return usuarioOpt.get().getPlaylists();
        }
        return null;
    }
}