package com.bosonit.virtualtravel.utils.exceptions.handle;
import lombok.Getter;

import java.util.Date;

@Getter
public class ExceptionResponse {
    private final Date timestamp;
    private final String mensaje;
    private final String detalles;
    private final String httpCodeMessage;

    public ExceptionResponse(Date timestamp, String message, String details, String httpCodeMessage) {
        super();
        this.timestamp = timestamp;
        this.mensaje = message;
        this.detalles = details;
        this.httpCodeMessage = httpCodeMessage;
    }

}
