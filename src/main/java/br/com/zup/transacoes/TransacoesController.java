package br.com.zup.transacoes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transacoes")
public class TransacoesController {

    private final TransacaoRepository transacaoRepository;
    private static final Logger log = LoggerFactory.getLogger(TransacoesController.class);

    public TransacoesController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @GetMapping("/cartao/{idCartao}")
    public ResponseEntity<List<TransacaoResponse>> consultarTransacoes(@PathVariable String idCartao) {
        log.info("Buscando pelo cartão {}", idCartao);

        List<TransacaoResponse> consulta =
                TransacaoResponse.paraLista(transacaoRepository.findTop10ByIdCartaoOrderByEfetivadaEmDesc(idCartao));

        if (consulta.isEmpty()) {
            log.warn("Não há transações para o cartão {}", idCartao);
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foram encontradas transações para esse " +
                   "cartão");
        }

        return ResponseEntity.ok(consulta);
    }

    @ExceptionHandler({ ResponseStatusException.class })
    public ResponseEntity<ErroResponse> responseStatusExceptionHandler(ResponseStatusException exception){
        ErroResponse response = new ErroResponse(exception.getReason());
        return ResponseEntity.status(exception.getStatus()).body(response);
    }

    static class ErroResponse {
        private String erro;

        public ErroResponse(String erro) {
            this.erro = erro;
        }

        public String getErro() {
            return erro;
        }
    }
}
