package br.com.campanha.api.domain;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Representa informações de erros que serão retornadas para o usuário
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */
@ApiModel(value="ErrorInfo", description="Representa informações de erros que serão retornadas para o usuário")
public class ErrorInfo {

    @ApiModelProperty(value = "API URL",dataType = "string", required = true)
    public final String url;

    @ApiModelProperty(value = "Mensagem de exceção",dataType = "string", required = true)
    public final String ex;

    public ErrorInfo(String url, Exception ex) {
        this.url = url;
        this.ex = ex.getLocalizedMessage();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("url", url)
                .add("ex", ex)
                .toString();
    }
}
