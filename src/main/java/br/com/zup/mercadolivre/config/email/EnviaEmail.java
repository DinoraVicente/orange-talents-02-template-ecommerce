package br.com.zup.mercadolivre.config.email;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public interface EnviaEmail {

    /**
     * @param dominio - podem ser enviadas apenas de um tipo especifico, liberados, por segurança
     *                exemplo: @gmail.com
     */
    void send(@NotBlank String corpo, @NotBlank String assunto, @NotBlank String remetente,
              @NotBlank @Email String dominio, @NotBlank @Email String destinatario);
}
