package br.edu.infnet.attDDD.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BandaDto {

    private UUID id;

    @NotEmpty(message = "Campo nome é obrigatório")
    private String nome;

    @NotEmpty(message = "Campo descricao é obrigatório")
    private String descricao;

    @Valid
    private List<MusicaDto> musicas;
}