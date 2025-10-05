package br.csi.politecnico.financecontrol.dto;

import lombok.Value;

@Value
public class ResponseDTO<T> {

    String message;
    T data;

    public ResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseDTO<T> ok (T data) {
        return new ResponseDTO<>(null, data);
    }

    public static <T> ResponseDTO<T> ok (String message, T data) {
        return new ResponseDTO<>(message, data);
    }

    public static <T> ResponseDTO<T> err (String message, T data) {
        return new ResponseDTO<>(message, data);
    }

    public static <T> ResponseDTO<T> err (String message) {
        return new ResponseDTO<>(message, null);
    }

}
