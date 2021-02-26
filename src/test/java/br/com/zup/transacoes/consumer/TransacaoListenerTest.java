package br.com.zup.transacoes.consumer;

import br.com.zup.transacoes.Transacao;
import br.com.zup.transacoes.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

class TransacaoListenerTest {
    private final TransacaoRepository transacaoRepository = Mockito.spy(TransacaoRepository.class);
    private final TransacaoListener listener = new TransacaoListener(transacaoRepository);

    @Test
    void deveSalvarUmaTransacaoAoReceberUmaMensagem(){
        TransacaoMessage message = new TransacaoMessage(UUID.randomUUID(), new BigDecimal("100"),
                                                        new EstabelecimentoMessage("Mercado", "Uberlandia",
                                                                                   "Av 1, 123"),
                                                        new CartaoMessage("1", "teste@teste.com"), LocalDateTime.now());
        List<String> mockHeader = List.of("1");

        listener.ouvir(message, mockHeader, mockHeader, mockHeader);
        verify(transacaoRepository, atLeastOnce()).save(any(Transacao.class));
    }

}