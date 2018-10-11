package br.ufrn.exceptionhandling;

public class ServerErrorDTO {
    private Integer status;
    private String message;
    private String exceptionClassName;

    public ServerErrorDTO() {
    }

    public ServerErrorDTO(Integer status, String message, String exceptionClassName) {
        this.status = status;
        this.message = message;
        this.exceptionClassName = exceptionClassName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public void setExceptionClassName(String exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
    }
}
