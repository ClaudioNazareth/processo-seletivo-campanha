package br.com.campanha.api.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="ErrorInfo", description="Representa informações de erros que serão retornadas para o usuário ")
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
        final StringBuilder sb = new StringBuilder("ErrorInfo{");
            sb.append("url='").append(url).append('\'');
            sb.append(", ex='").append(ex).append('\'');
            sb.append('}');
        return sb.toString();
    }
}
