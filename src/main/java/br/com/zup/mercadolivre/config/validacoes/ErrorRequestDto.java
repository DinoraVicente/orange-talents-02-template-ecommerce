package br.com.zup.mercadolivre.config.validacoes;

public class ErrorRequestDto {

    private String campo;
    private String erro;

    @Deprecated
    public ErrorRequestDto() {
    }

    public ErrorRequestDto(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}