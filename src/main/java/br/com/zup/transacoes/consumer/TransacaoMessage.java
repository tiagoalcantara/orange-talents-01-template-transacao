package br.com.zup.transacoes.consumer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransacaoMessage {
    private UUID id;
    private BigDecimal valor;
    private EstabelecimentoMessage estabelecimento;
    private CartaoMessage cartao;
    private LocalDateTime efetivadaEm;

    @Deprecated
    public TransacaoMessage(){}

    public TransacaoMessage(UUID id,
                            BigDecimal valor,
                            EstabelecimentoMessage estabelecimento,
                            CartaoMessage cartao,
                            LocalDateTime efetivadaEm) {
        this.id = id;
        this.valor = valor;
        this.estabelecimento = estabelecimento;
        this.cartao = cartao;
        this.efetivadaEm = efetivadaEm;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public EstabelecimentoMessage getEstabelecimento() {
        return estabelecimento;
    }

    public CartaoMessage getCartao() {
        return cartao;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }

    @Override
    public String toString() {
        return "TransacaoMessage{" +
                "id=" + id +
                ", valor=" + valor +
                ", estabelecimento=" + estabelecimento +
                ", cartao=" + cartao +
                ", efetivadaEm=" + efetivadaEm +
                '}';
    }
}
