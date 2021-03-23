package br.com.zup.mercadolivre.produtos;

import br.com.zup.mercadolivre.categorias.Categoria;
import br.com.zup.mercadolivre.produtos.caracteristicas.CaracteristicaRequest;
import br.com.zup.mercadolivre.usuarios.SenhaLimpa;
import br.com.zup.mercadolivre.usuarios.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
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
        Usuario dono = new Usuario("teste@teste.com.br", new SenhaLimpa("testesenha"));

        new Produto("nome", BigDecimal.TEN, 10, caracteristicas, "descricao",categoria, LocalDate.now(), dono);
    }

    static Stream<Arguments> geradorTesteCaracteristica(){
        return Stream.of(
                Arguments.of(
                        List.of(new CaracteristicaRequest("bla1", "blaa1"),
                                new CaracteristicaRequest("bla2", "blaa2"),
                                new CaracteristicaRequest("blaa3", "blaa3"))));
    }

    @MethodSource("geradorTesteCaracteristicaErro")
    @ParameterizedTest
    @DisplayName("produto nao pode ter menos de 3 caracteristicas")
    void testeCaracteristicaErro(Collection<CaracteristicaRequest> caracteristicas) {
        Categoria categoria = new Categoria("Tecnologia");
        Usuario dono = new Usuario("teste@teste.com.br", new SenhaLimpa("testesenha"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Produto("nome", BigDecimal.TEN,
                10, caracteristicas, "descricao", categoria, LocalDate.now(), dono));
    }

    static Stream<Arguments> geradorTesteCaracteristicaErro(){
        return Stream.of(
                Arguments.of(
                        List.of(new CaracteristicaRequest("bla1", "blaa1"),
                                new CaracteristicaRequest("bla2", "blaa2"))),
                Arguments.of(
                        List.of(new CaracteristicaRequest("bla4", "blaa4"))));
    }

    @CsvSource({"1,1,true", "1,5,false", "3,2,true", "1,6,false"})
    @ParameterizedTest
    @DisplayName("deve verificar estoque do produto")
    void testeEstoque(int estoque, int qntdPedida, boolean resultadoEsperado) {
        List<CaracteristicaRequest> caracteristicas = List.of(new CaracteristicaRequest("chave", "valor"),
                                                              new CaracteristicaRequest("chave1", "valor1"),
                                                              new CaracteristicaRequest("chave2", "valor2"));
        Categoria categoria = new Categoria("Tecnologia");
        Usuario dono = new Usuario("teste@teste.com.br", new SenhaLimpa("testesenha"));

        Produto produto = new Produto("nome", BigDecimal.TEN, estoque, caracteristicas,
                "descricao", categoria, LocalDate.now(), dono);

        boolean result = produto.abateEstoque(qntdPedida);

        Assertions.assertEquals(resultadoEsperado, result);
    }

    @CsvSource({"0", "-1", "-120"})
    @ParameterizedTest
    @DisplayName("nao deve abater estoque <=zero")
    void testeEstoque2(int estoque) {
        List<CaracteristicaRequest> caracteristicas = List.of(new CaracteristicaRequest("chave", "valor"),
                new CaracteristicaRequest("chave1", "valor1"),
                new CaracteristicaRequest("chave2", "valor2"));
        Categoria categoria = new Categoria("Tecnologia");
        Usuario dono = new Usuario("teste@teste.com.br", new SenhaLimpa("testesenha"));
        Produto produto = new Produto("nome", BigDecimal.TEN, 10, caracteristicas,
                "descricao", categoria, LocalDate.now(), dono);

        Assertions.assertThrows(IllegalArgumentException.class, () -> produto.abateEstoque(estoque));
    }
}