package br.edu.infnet.attDDD.service;

import br.edu.infnet.attDDD.domain.Banda;
import br.edu.infnet.attDDD.domain.Plano;
import br.edu.infnet.attDDD.repository.PlanoRepository;
import br.edu.infnet.attDDD.service.dto.BandaDto;
import br.edu.infnet.attDDD.service.dto.PlanoDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    public PlanoDto criarPlano(PlanoDto planoDto) {
        Plano plano = new Plano();
        BeanUtils.copyProperties(planoDto, plano);
        Plano planoSalvo = planoRepository.save(plano);
        return toDto(planoSalvo);
    }

    private PlanoDto toDto(Plano plano) {
        PlanoDto planoDto = new PlanoDto();
        BeanUtils.copyProperties(plano, planoDto);
        return planoDto;
    }
}