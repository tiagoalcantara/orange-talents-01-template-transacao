package br.com.zup.transacoes.consumer;

import br.com.zup.transacoes.Transacao;
import br.com.zup.transacoes.TransacaoRepository;
import org.apache.kafka.common.errors.InvalidTopicException;
import org.apache.kafka.common.errors.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Component
public class TransacaoListener {

    private final TransacaoRepository transacaoRepository;
    private final Logger log = LoggerFactory.getLogger(TransacaoListener.class);

    public TransacaoListener(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    public void ouvir(TransacaoMessage transacaoMessage,
                      @Header (KafkaHeaders.RECEIVED_PARTITION_ID) List<?> partitions,
                      @Header (KafkaHeaders.RECEIVED_TOPIC) List<?> topics,
                      @Header (KafkaHeaders.OFFSET) List<?> offset){
        System.out.println("----- MENSAGEM RECEBIDA -----");
        System.out.println(transacaoMessage);
        System.out.println("Partition: " + partitions.get(0));
        System.out.println("Topic: " + topics.get(0));
        System.out.println("Offset: " + offset.get(0));

        Transacao transacao = new Transacao(transacaoMessage.getValor(),
                                            transacaoMessage.getCartao().getId(),
                                            transacaoMessage.getEfetivadaEm(),
                                            transacaoMessage.getEstabelecimento().getNome());

        transacaoRepository.save(transacao);

        log.info("Transação salva: {}", transacao.getId());

        System.out.println("-----------------------------");
    }

    @ExceptionHandler({InvalidTopicException.class})
    public void invalidTopicHandler(){
        log.error("O tópico é inválido. Ele será criado se a opção estiver habilitada.");
    }

    @ExceptionHandler({SerializationException.class})
    public void serializationHandler(){
        log.error("Erro na serialização.");
    }
}
