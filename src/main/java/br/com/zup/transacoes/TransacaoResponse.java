package br.com.zup.transacoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransacaoResponse {
    private final UUID id;
    private final BigDecimal valor;
    private final LocalDateTime efetivadaEm;
    private final String estabelecimento;

    public TransacaoResponse(Transacao transacao){
        this.id = transacao.getId();
        this.valor = transacao.getValor();
        this.efetivadaEm = transacao.getEfetivadaEm();
        this.estabelecimento = transacao.getEstabelecimento();
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public static List<TransacaoResponse> paraLista(List<Transacao> lista) {
        return lista.stream().map(TransacaoResponse::new).collect(Collectors.toList());
    }
}
