package br.com.zup.mercadolivre.config.email;

import org.springframework.stereotype.Component;

@Component
public class EnviaEmailFake implements EnviaEmail{

    @Override
    public void send(String corpo, String assunto, String remetente, String dominio, String destinatario) {
        System.out.println(corpo);
        System.out.println(assunto);
        System.out.println(remetente);
        System.out.println(dominio);
        System.out.println(destinatario);
    }
}