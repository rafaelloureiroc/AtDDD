package br.edu.infnet.attDDD.service;

import br.edu.infnet.attDDD.domain.Banda;
import br.edu.infnet.attDDD.domain.Musica;
import br.edu.infnet.attDDD.repository.BandaRepository;
import br.edu.infnet.attDDD.repository.MusicaRepository;
import br.edu.infnet.attDDD.service.dto.MusicaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicaService {

    @Autowired
    MusicaRepository musicaRepository;

    @Autowired
    BandaRepository bandaRepository;

    public MusicaDto criarMusica(MusicaDto musicaDto) throws Exception {
        Optional<Banda> optionalBanda = bandaRepository.findById(musicaDto.getIdBanda());
        if (optionalBanda.isEmpty()) {
            throw new Exception("Banda não encontrada com o ID fornecido: " + musicaDto.getIdBanda());
        }

        Banda banda = optionalBanda.get();

        Musica musica = new Musica();
        BeanUtils.copyProperties(musicaDto, musica);
        musica.setBanda(banda);

        Musica musicaSalva = musicaRepository.save(musica);

        return toDto(musicaSalva);
    }

    private MusicaDto toDto(Musica musica) {
        MusicaDto musicaDto = new MusicaDto();
        BeanUtils.copyProperties(musica, musicaDto);
        musicaDto.setIdBanda(musica.getBanda().getId());
        return musicaDto;
    }
}