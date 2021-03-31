package br.com.zup.mercadolivre.pedidos;

import br.com.zup.mercadolivre.config.email.Emails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventosNovaCompra implements EventoCompraSucesso{

    @Autowired
    private Set<EventoCompraSucesso> eventoCompraSucessos;

    @Autowired
    private Emails email;

    public EventosNovaCompra(Set<EventoCompraSucesso> eventos) {
        this.eventoCompraSucessos = eventos;
    }

    public EventosNovaCompra(Set<EventoCompraSucesso> eventos, Emails email) {
        this.eventoCompraSucessos = eventos;
        this.email = email;
    }

    @Override
    public void processa(Compra compra) {
        if(compra.processadaComSucesso()){
            eventoCompraSucessos.forEach(evento -> evento.processa(compra));
        }else{
            email.erroPagamento(compra);
        }
    }
}