package br.com.zup.mercadolivre.produtos;

import br.com.zup.mercadolivre.categorias.Categoria;
import br.com.zup.mercadolivre.produtos.caracteristicas.CaracteristicaRequest;
import br.com.zup.mercadolivre.usuarios.SenhaLimpa;
import br.com.zup.mercadolivre.usuarios.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@Profile("test")
class ProdutoControllerTest {

    @MethodSource("geradorTesteCaracteristica")
    @ParameterizedTest
    @DisplayName("deve cadastrar imagem do produto apenas com o dono do produto")
    void testeImagemProdutoUsuario(Collection<CaracteristicaRequest> caracteristicas) {
        Categoria categoria = new Categoria("Tecnologia");
        Usuario usuario = new Usuario("fulano@email.com", new SenhaLimpa("123456"));

        Produto produto = new Produto("nome", new BigDecimal(3500), 2,
                caracteristicas, "descricao", categoria, LocalDate.now(), usuario);

    }

    static Stream<Arguments> geradorTesteCaracteristica() {
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


}