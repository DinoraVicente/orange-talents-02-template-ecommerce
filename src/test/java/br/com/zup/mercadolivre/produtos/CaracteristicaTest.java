package br.com.zup.mercadolivre.produtos;

import br.com.zup.mercadolivre.produtos.caracteristicas.CaracteristicaRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class CaracteristicaTest {

    @ParameterizedTest
    @MethodSource("geradorTesteCaracteristica")
    @DisplayName("nao deveria cadastrar duas caracteristicas com nomes iguais")
    void testeCaracteristicaNomeIgual(int resultado, List<CaracteristicaRequest> caracteristicas) {
        ProdutoRequest request = new ProdutoRequest("nome", BigDecimal.TEN, 10,
                caracteristicas, "descricao", 1L);

        Assertions.assertEquals(resultado, request.buscaPorCaracteristicasIguais().size());
    }

    static Stream<Arguments> geradorTesteCaracteristica(){
        return Stream.of(
                Arguments.of(0, List.of()),
                Arguments.of(0, List.of(new CaracteristicaRequest("nome", "valor"))),
                Arguments.of(0, List.of(new CaracteristicaRequest("nome", "valor"), new CaracteristicaRequest("nome1", "valor1"))),
                Arguments.of(1, List.of(new CaracteristicaRequest("nome", "valor"),(new CaracteristicaRequest("nome", "valor")))));
    }
}