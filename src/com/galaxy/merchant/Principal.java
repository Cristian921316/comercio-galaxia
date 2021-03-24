package com.galaxy.merchant;

import java.util.ArrayList;


public class Principal {


	public static void main(String[] args) {
		
		System.out.println("SYSTEMA INTERCAMBIO DE GALAXIAS, dar doble enter para finalizar entradas.");
		
		// Initialize a new paragraph
		ValidacionesEntrada validacion = new ValidacionesEntrada();
		
		
		// se lee las entradas y ves validado se almacena en una lista de String prev
		ArrayList<String> output=validacion.read();
		
		for(int i=0;i<output.size();i++)
		{
			System.out.println(output.get(i));
		}
		
		
	}

}
