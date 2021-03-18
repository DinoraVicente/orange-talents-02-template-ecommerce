package br.com.zup.mercadolivre.produtos.imagens;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UploadFile {

    /**
     * @return transforma esses dados em uma lista,
     * e para cada imagem pega o nome original do arquivo.
     */
    public Set<String> envia(List<MultipartFile> imagens) {
        return imagens.stream().map(imagem -> "http://www." +
                imagem.getOriginalFilename()).collect(Collectors.toSet());
    }
}