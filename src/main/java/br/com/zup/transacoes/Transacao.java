package br.com.zup.transacoes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transacao {
    @Id @GeneratedValue
    private UUID id;

    @NotNull @Positive
    @Column(nullable = false)
    private BigDecimal valor;

    @NotBlank
    @Column(nullable = false)
    private String idCartao;

    @Column(nullable = false)
    private LocalDateTime efetivadaEm;

    @NotBlank
    @Column(nullable = false)
    private String estabelecimento;

    @Deprecated
    public Transacao(){}

    public Transacao(@NotNull @Positive BigDecimal valor,
                     @NotBlank String idCartao,
                     LocalDateTime efetivadaEm,
                     @NotBlank String estabelecimento) {
        this.valor = valor;
        this.idCartao = idCartao;
        this.efetivadaEm = efetivadaEm;
        this.estabelecimento = estabelecimento;
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

    public UUID getId() {
        return id;
    }
}
