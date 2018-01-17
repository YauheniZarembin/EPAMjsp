package com.zarembin.epampjsp.exception;

public class ReceiverException extends Exception {
    public  ReceiverException(){
    }
    public  ReceiverException(String message){
        super(message);
    }
    public  ReceiverException(Throwable cause){
        super(cause);
    }
    public  ReceiverException(String message , Throwable cause){
        super(message, cause);
    }
}
