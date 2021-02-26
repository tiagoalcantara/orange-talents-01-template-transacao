package br.com.zup.transacoes;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TransacoesControllerTest {

    private final TransacaoRepository transacaoRepository = Mockito.mock(TransacaoRepository.class);
    private final TransacoesController controller = new TransacoesController(transacaoRepository);

    @Test
    void deveRetornar200ETransacoesQuandoExistirem(){
        Transacao transacao = Mockito.mock(Transacao.class);
        List<Transacao> transacaoLista = List.of(transacao, transacao, transacao);

        when(transacaoRepository.findTop10ByIdCartaoOrderByEfetivadaEmDesc(anyString()))
                .thenReturn(transacaoLista);

        ResponseEntity<List<TransacaoResponse>> response = controller.consultarTransacoes(anyString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transacaoLista.size(), Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void deveRetornar404QuandoNaoEncontrarTransacoes(){
        when(transacaoRepository.findTop10ByIdCartaoOrderByEfetivadaEmDesc(anyString()))
                .thenReturn(new ArrayList<>());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            controller.consultarTransacoes(anyString());
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}