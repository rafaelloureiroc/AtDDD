package br.edu.infnet.attDDD.service;

import br.edu.infnet.attDDD.domain.Banda;
import br.edu.infnet.attDDD.repository.BandaRepository;
import br.edu.infnet.attDDD.service.dto.BandaDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BandaService {

    @Autowired
    BandaRepository bandaRepository;

    public BandaDto criarBanda(BandaDto bandaDto) {
        Banda banda = new Banda();
        BeanUtils.copyProperties(bandaDto, banda);
        Banda bandaSalva = bandaRepository.save(banda);
        return toDto(bandaSalva);
    }

    private BandaDto toDto(Banda banda) {
        BandaDto bandaDto = new BandaDto();
        BeanUtils.copyProperties(banda, bandaDto);
        return bandaDto;
    }
}