package com.example.buoi2.utils;

public class ApiResponse<T> {
    private String message;
    private int status;
    private T responese;

    public ApiResponse(String message, int statusCode, T data) {
        this.message = message;
        this.status = statusCode;
        this.responese = data;
    }

    public ApiResponse(String message, int statusCode) {
        this.message = message;
        this.status = statusCode;
        this.responese = null;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return responese;
    }

    public void setData(T data) {
        this.responese = data;
    }
}
