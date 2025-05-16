package com.cambio_service.controllers;

import com.cambio_service.models.Cambio;
import com.cambio_service.repositories.CambioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("cambio-service")
public class CambioController {

    private static final Logger log = LoggerFactory.getLogger(CambioController.class);
    @Autowired
    private Environment environment;

    @Autowired
    private CambioRepository repository;

    //http://localhost:8000/cambio-service/5/USD/BRL
    @GetMapping(value="/{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable("amount")BigDecimal amount,
                            @PathVariable("from") String from,
                            @PathVariable("to") String to){
        //A diferença entre --server.port e -Dserver.port no contexto de uma aplicação Spring Boot está relacionada ao modo como as propriedades são passadas para a aplicação Java
        //java -jar sua-aplicacao.jar --server.port=8001
        //java -Dserver.port=8001 -jar sua-aplicacao.jar
        //Recomendação: Para configurações específicas do Spring Boot, como a porta do servidor, é preferível usar --server.port para garantir que a configuração seja aplicada corretamente.
        //Contexto: Use -D para propriedades de sistema gerais que não são específicas do Spring Boot.
        var port = environment.getProperty("local.server.port");
        log.info("server.port=" + environment.getProperty("server.port"));
        log.info("local.server.port=" + environment.getProperty("local.server.port"));

        var cambio = repository.findByFromAndTo(from, to);
        if (cambio == null){
            throw  new RuntimeException("Currency Unsupported");
        }

        BigDecimal conversionFactor = cambio.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);

        cambio.setEnvironment(port);
        cambio.setConvertedValue(convertedValue);

        //return new Cambio(1L, from, to, BigDecimal.ONE, BigDecimal.ONE, port);
        return cambio;
    }
}
