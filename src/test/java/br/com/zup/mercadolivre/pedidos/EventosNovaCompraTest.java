package br.com.zup.mercadolivre.pedidos;

import br.com.zup.mercadolivre.config.email.Emails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Set;

class EventosNovaCompraTest {

    @Mock
    private Emails emails;

    @Test
    @DisplayName("deveria lancar eventos de sucesso")
    void teste(){
        Compra compraConcluida = CompraBuilders.CompraFixture.novaCompra().concluida();
        EventoCompraSucesso eventoSucesso = Mockito.mock(EventoCompraSucesso.class);
        Set<EventoCompraSucesso> eventos = Set.of(eventoSucesso);
        EventosNovaCompra eventosNovaCompra = new EventosNovaCompra(eventos);

        eventosNovaCompra.processa(compraConcluida);
        //verifica com o equals
        Mockito.verify(eventoSucesso).processa(compraConcluida);
    }

    @Test
    @DisplayName("deveria lancar eventos de falha")
    void teste2(){
        Compra compraErro = CompraBuilders.CompraFixture.novaCompra().naoConcluida();
        EventoCompraSucesso eventoSucesso = Mockito.mock(EventoCompraSucesso.class);
        Set<EventoCompraSucesso> eventos = Set.of(eventoSucesso);
        EventosNovaCompra eventosNovaCompra = new EventosNovaCompra(eventos, emails);

        eventosNovaCompra.processa(compraErro);

        Mockito.verify(emails).erroPagamento(compraErro);
    }
}