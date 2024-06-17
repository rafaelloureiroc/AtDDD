package br.edu.infnet.attDDD.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class MusicaDto {

    private UUID id;

    @NotEmpty(message = "Campo nome é obrigatório")
    private String nome;

    @NotNull(message = "Campo duração é obrigatório")
    private Integer duracao;

    @NotNull(message = "A banda não pode ser nula")
    private UUID idBanda;
}
