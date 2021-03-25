package br.com.zup.mercadolivre.endpointfake;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class NotasERankingController {

    @PostMapping("/notas-fiscais")
    public void criaNota(@Valid @RequestBody NFRequest request) throws InterruptedException{
        System.out.println("Criando nota para " + request.getIdCompra() + " do comprador " + request.getIdComprador());
        Thread.sleep(150);
    }
//
//    @PostMapping("/ranking")
//    public void rankingVendedores(@Valid @RequestBody RankingRequest request) throws InterruptedException{
//        System.out.println("Criando nota para " + request.getIdCompra() + " do comprador " + request.getIdComprador());
//        Thread.sleep(150);
//    }
}