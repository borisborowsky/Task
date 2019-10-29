package com.company.exception;

public class NoFineException extends Exception {
	  public NoFineException() { super(); }
	  public NoFineException(String message) { 
		  super(message); 
	  }
	  public NoFineException(String message, Throwable cause) { 
		  super(message, cause);
    }
	  public NoFineException(Throwable cause) {
		  super(cause); 
    }
}
