package br.edu.infnet.attDDD.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String numero;

    @Column
    private Boolean ativo;

    @Column
    private LocalDate validade;

    @Column
    private double limite;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Transacao> transacoes = new ArrayList<>();

    private int TRANSACAO_INTERVALO_TEMPO = 2;
    private int TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA = 3;
    private int TRANSACAO_MERCHANT_DUPLICADAS = 2;

    public void criarTransacao(String merchant, double valor, String descricao) throws Exception {
        // Validar Cartao
        if (!this.ativo) {
            throw new Exception("Cartão não está ativo");
        }

        Transacao transacao = new Transacao();
        transacao.setMerchant(merchant);
        transacao.setDescricao(descricao);
        transacao.setValor(valor);
        transacao.setDtTransacao(LocalDateTime.now());
        transacao.setCartao(this);

        // Verificar Limite
        if (!validarLimite(transacao)) {
            throw new Exception("Cartão não possui limite suficiente para esta transação");
        }

        // Validar Transacao
        if (!validarTransacao(transacao)) {
            throw new Exception("Transação inválida de acordo com as regras estabelecidas");
        }

        // Diminuir o limite do cartão
        this.setLimite(this.getLimite() - transacao.getValor());

        // Adicionar a nova transação à lista de transações do cartão
        this.transacoes.add(transacao);
    }

    boolean validarTransacao(Transacao transacao) {
        List<Transacao> ultimasTransacoes = this.getTransacoes().stream()
                .filter(x -> x.getDtTransacao().isAfter(LocalDateTime.now().minusMinutes(this.TRANSACAO_INTERVALO_TEMPO)))
                .toList();

        if (ultimasTransacoes.size() >= this.TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA) {
            return false;
        }

        long transacoesMerchantRepetidas = ultimasTransacoes.stream()
                .filter(x -> x.getMerchant().equals(transacao.getMerchant())
                        && x.getValor() == transacao.getValor())
                .count();

        return transacoesMerchantRepetidas < this.TRANSACAO_MERCHANT_DUPLICADAS;
    }

    private boolean validarLimite(Transacao transacao) {
        return this.limite >= transacao.getValor();
    }
}