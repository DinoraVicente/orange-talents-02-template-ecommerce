package br.com.zup.mercadolivre.produtos;

import br.com.zup.mercadolivre.categorias.Categoria;
import br.com.zup.mercadolivre.produtos.caracteristicas.CaracteristicaRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

class ProdutoTest {

    @MethodSource("geradorTesteCaracteristica")
    @ParameterizedTest
    @DisplayName("produto precisa de 3 caracteristicas")
    void testeCaracteristica(Collection<CaracteristicaRequest> caracteristicas) {
        Categoria categoria = new Categoria("Tecnologia");

        new Produto("nome", BigDecimal.TEN, 10, caracteristicas, "descricao",categoria, LocalDate.now(), null);
    }

    static Stream<Arguments> geradorTesteCaracteristica(){
        return Stream.of(
                Arguments.of(
                        List.of(new CaracteristicaRequest("bla1", "blaa1"),
                                new CaracteristicaRequest("bla2", "blaa2"),
                                new CaracteristicaRequest("blaa3", "blaa3"))),
                Arguments.of(
                        List.of(new CaracteristicaRequest("bla4", "blaa4"),
                                new CaracteristicaRequest("bla5", "blaa5"),
                                new CaracteristicaRequest("bla6", "blaa6"))));
    }

    @MethodSource("geradorTesteCaracteristicaErro")
    @ParameterizedTest
    @DisplayName("produto nao pode ter menos de 3 caracteristicas")
    void testeCaracteristicaErro(Collection<CaracteristicaRequest> caracteristicas) {
        Categoria categoria = new Categoria("Tecnologia");

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Produto("nome", BigDecimal.TEN,
                10, caracteristicas, "descricao", categoria, LocalDate.now(), null));
    }

    static Stream<Arguments> geradorTesteCaracteristicaErro(){
        return Stream.of(
                Arguments.of(
                        List.of(new CaracteristicaRequest("bla1", "blaa1"),
                                new CaracteristicaRequest("bla2", "blaa2"))),
                Arguments.of(
                        List.of(new CaracteristicaRequest("bla4", "blaa4"))));
    }
}