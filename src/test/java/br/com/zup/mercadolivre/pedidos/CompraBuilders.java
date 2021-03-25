package br.com.zup.mercadolivre.pedidos;

import br.com.zup.mercadolivre.categorias.Categoria;
import br.com.zup.mercadolivre.produtos.Produto;
import br.com.zup.mercadolivre.produtos.caracteristicas.CaracteristicaRequest;
import br.com.zup.mercadolivre.usuarios.SenhaLimpa;
import br.com.zup.mercadolivre.usuarios.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CompraBuilders {

    public static class CompraFixture{
        private Compra novaCompra;

        public CompraFixture(Compra novaCompra) {
            super();
            this.novaCompra = novaCompra;
        }

        public Compra concluida(){
            RetornoGatewayPagamento retornoSucesso = novaCompra ->
                    new Transacao(StatusTransacao.sucesso, "1", novaCompra);
            this.novaCompra.addTransacao(retornoSucesso);
            return novaCompra;
        }

        public Compra naoConcluida(){
            RetornoGatewayPagamento retornoErro = novaCompra ->
                    new Transacao(StatusTransacao.erro, "1", novaCompra);
            this.novaCompra.addTransacao(retornoErro);
            return novaCompra;
        }

        public static CompraFixture novaCompra() {
            Categoria categoria = new Categoria("Tecnologia");
            Usuario usuario = new Usuario("fulano@email.com", new SenhaLimpa("123456"));
            List<CaracteristicaRequest> caracteristica = List.of(new CaracteristicaRequest("bla1", "blaa1"),
                    new CaracteristicaRequest("bla2", "blaa2"),
                    new CaracteristicaRequest("blaa3", "blaa3"));

            Produto produto = new Produto("nome", new BigDecimal(3500), 2,
                    caracteristica, "descricao", categoria, LocalDate.now(), usuario);

            Usuario comprador = new Usuario("fulano1@email.com", new SenhaLimpa("123456"));

            GatewayPagamento gatewayPagamento = GatewayPagamento.PAGSEGURO;
            return new CompraFixture(new Compra(produto, 100, comprador, gatewayPagamento));
        }
    }
}
