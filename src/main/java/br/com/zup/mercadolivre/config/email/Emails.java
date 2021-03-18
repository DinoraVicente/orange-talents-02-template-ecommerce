package br.com.zup.mercadolivre.config.email;

import br.com.zup.mercadolivre.produtos.perguntas.Perguntas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class Emails {

    @Autowired
    private EnviaEmail enviaEmail;

    public void novaPergunta(@NotNull @Valid Perguntas pergunta) {

        enviaEmail.send("<html>...</html>", "Nova pergunta", pergunta.getUsuario().getEmail(),
                "novapergunta@nossomercadolivre.com", pergunta.getDonoProduto().getEmail());
    }
}