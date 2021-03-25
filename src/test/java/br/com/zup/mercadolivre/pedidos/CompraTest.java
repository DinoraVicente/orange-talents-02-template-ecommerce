package br.com.zup.mercadolivre.pedidos;

import br.com.zup.mercadolivre.categorias.Categoria;
import br.com.zup.mercadolivre.produtos.Produto;
import br.com.zup.mercadolivre.produtos.caracteristicas.CaracteristicaRequest;
import br.com.zup.mercadolivre.usuarios.SenhaLimpa;
import br.com.zup.mercadolivre.usuarios.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class CompraTest {

    private Compra novaCompra;

    @BeforeEach
    void init() {
        Categoria categoria = new Categoria("Tecnologia");
        Usuario usuario = new Usuario("fulano@email.com", new SenhaLimpa("123456"));
        List<CaracteristicaRequest> caracteristica = List.of(new CaracteristicaRequest("bla1", "blaa1"),
                                                             new CaracteristicaRequest("bla2", "blaa2"),
                                                             new CaracteristicaRequest("blaa3", "blaa3"));

        Produto produto = new Produto("nome", new BigDecimal(3500), 2,
                caracteristica, "descricao", categoria, LocalDate.now(), usuario);
        novaCompra = new Compra(produto, 100, usuario, GatewayPagamento.PAGSEGURO);

    }

    @Test
    @DisplayName("deve adicionar uma nova transacao")
    void teste(){
        RetornoGatewayPagamento retornoGatewayPagamento = compra ->
                new Transacao(StatusTransacao.sucesso, "1", compra);

        novaCompra.addTransacao(retornoGatewayPagamento);
    }

    @Test
    @DisplayName("nao deve adicionar transacoes iguais")
    void teste1(){
        RetornoGatewayPagamento retornoGatewayPagamento = compra ->
                new Transacao(StatusTransacao.erro, "1", compra);

        novaCompra.addTransacao(retornoGatewayPagamento);

        RetornoGatewayPagamento retornoGatewayPagamento1 = compra ->
                new Transacao(StatusTransacao.erro, "1", compra);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                novaCompra.addTransacao(retornoGatewayPagamento1));
    }

    @Test
    @DisplayName("nao pode aceitar transacao se a compra ja tiver sido concluida")
    void teste2(){
        RetornoGatewayPagamento retornoGatewayPagamento = compra ->
                new Transacao(StatusTransacao.sucesso, "1", compra);

        novaCompra.addTransacao(retornoGatewayPagamento);

        RetornoGatewayPagamento retornoGatewayPagamento1 = compra ->
                new Transacao(StatusTransacao.sucesso, "2", compra);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                novaCompra.addTransacao(retornoGatewayPagamento1));
    }

    @DisplayName("deveria verificar se uma compra foi concluida com sucesso")
    @ParameterizedTest
    @MethodSource("geradorTeste3")
    void teste3(boolean resultadoEsperado, Collection<RetornoGatewayPagamento> retornoGatewayPagamentos){
        retornoGatewayPagamentos.forEach(retornoGatewayPagamento -> novaCompra.addTransacao(retornoGatewayPagamento));
        Assertions.assertEquals(resultadoEsperado, novaCompra.processadaComSucesso());
    }

    private static Stream<Arguments> geradorTeste3(){
        RetornoGatewayPagamento retornoDeSucesso = compra ->
                new Transacao(StatusTransacao.sucesso, "1", compra);

        RetornoGatewayPagamento retornoDeErro = compra ->
                new Transacao(StatusTransacao.erro, "1", compra);

        return Stream.of(
                Arguments.of(true, List.of(retornoDeSucesso)),
                Arguments.of(false, List.of(retornoDeErro)),
                Arguments.of(false, List.of()));
    }
}