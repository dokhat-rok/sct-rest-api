package com.sct.rest.api.access;

public class WrongPasswordException extends Exception{
    public WrongPasswordException(){

    }
    public WrongPasswordException(String e){
        super(e);
    }
}
