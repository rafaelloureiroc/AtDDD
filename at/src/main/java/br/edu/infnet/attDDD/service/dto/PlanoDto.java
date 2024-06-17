package br.edu.infnet.attDDD.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PlanoDto {
    private UUID id;

    @NotEmpty(message = "Campo nome é obrigatório")
    private String nome;

    @NotNull(message = "Campo ativo é obrigatório")
    private Boolean ativo;

    @NotEmpty(message = "Campo descrição é obrigatório")
    private String descricao;

    @NotNull(message = "Campo valor é obrigatório")
    private Double valor;
}