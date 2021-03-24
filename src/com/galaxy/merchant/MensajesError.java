/**
 * 
 */
package com.galaxy.merchant;

public class MensajesError {

	
	public MensajesError(){
		
	}
	
	
	public void printMessage(CodigosError error)
	{
		String message= getMessage(error);
		
		if(message !=null)
		System.out.println(message);
		
	}
	
	public String getMessage(CodigosError error)
	{
		String message= null;
		
		switch(error)
		{
			case NO_INPUT : message = "Se ha ingresado parametros de entrada";
							break;
			case INVALID : message = "La entrada no cumple con lo establecido";break;
			
			case INVALID_ROMAN_CHARACTER : message = "Caracter mal ingresado de numero romano";break;
			
			case INVALID_ROMAN_STRING : message =  "Numero romando equivocado";break;
			
			case INCORRECT_LINE_TYPE : message =  "Excepcion al ingresar entradas";break;
			
			case NO_IDEA : message = "No tengo idea de lo que estas hablando";break;
			
			default : break;
		}
		return message;
	}
}
