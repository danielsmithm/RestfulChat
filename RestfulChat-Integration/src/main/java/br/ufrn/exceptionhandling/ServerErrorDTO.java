package br.ufrn.exceptionhandling;

public class ServerErrorDTO {
    private Integer status;
    private String message;
    private String exceptionClassName;

    public ServerErrorDTO(Integer status, String message, String exceptionClassName) {
        this.status = status;
        this.message = message;
        this.exceptionClassName = exceptionClassName;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

}
