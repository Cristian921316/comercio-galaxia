/**
 * 
 */
package com.galaxy.merchant;

import java.util.HashMap;

public class NumeroRomanos {
	
	public static final MensajesError eMessage = new MensajesError();
	
	
	//Expresion para validar los numeros en letras tales como I,X,L.....D que son ingresdas x teclado
	public static String romanNumberValidator = "^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
	
	public static HashMap<String, Integer>  numerosPrincipalesRomanos =
			new HashMap<>();
	
	//se colocan los valores correspondientes
	public NumeroRomanos()
	{
		numerosPrincipalesRomanos.put("I", 1);
		numerosPrincipalesRomanos.put("V", 5);
		numerosPrincipalesRomanos.put("X", 10);
		numerosPrincipalesRomanos.put("L", 50);
		numerosPrincipalesRomanos.put("C", 100);
		numerosPrincipalesRomanos.put("D", 500);
		numerosPrincipalesRomanos.put("M", 1000);
	}
	
	//Se crea un grupo de constantes para igualar las letras ya conocidas a sus cantidades
	enum Roman{
		
		
		I(1) , V(5), X(10), L(50), C(100), D(500), M(1000);
		
		private int value;
		
		Roman(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return this.value;
		}
	}
	
	
	//funcion para determinar el caracter romano y extraer su valor en decimales
	@SuppressWarnings("unlikely-arg-type")
	private static int getValueFromRomanChar(char romanChar)
	{
		int value = -1;
		
		switch(romanChar)
		{
			case 'I' : value = 1;
						break;
			case 'V' : value = 5;
						break;
			case 'X' : value = 10;
						break;
			case 'L' : value = 50;
						break;
			
			case 'C' : value = 100;
						break;
			
			case 'D' : value = 500;
						break;
			
			case 'M' : value = 1000;
						break;
			
		}
		
		return value;
	}
	
	
	public static String romanToArabic(String roman)
	{
		String result="";
		
		switch(validateRomanNumber(roman))
		{
			case 1 :  result = convert(roman);
					  break;
				
			default : result = NumeroRomanos.eMessage.getMessage(CodigosError.INVALID_ROMAN_STRING);
		}
		
		return result;
	}
	
	
	
	private static int validateRomanNumber(String roman)
	{
		int result = 0;
		
		if(roman.matches(romanNumberValidator))
		{
			result = 1;
		}
		
		return result;
	}
	
	
	//convertir el numero romano segun las reglas conocidas 
	//por ejm Si el numero de lado es menor se resta del superior
	private static String convert(String roman)
	{
		int decimal = 0;
        int lastNumber = 0;
        
		for(int i=roman.length()-1;i>=0;i--)
		{
			char ch = roman.charAt(i);
			decimal = CheckRoman(getValueFromRomanChar(ch), lastNumber, decimal);
            lastNumber = getValueFromRomanChar(ch);
		}
		
		return decimal+"";
		
	}
	
	
	private static int CheckRoman(int TotalDecimal, int LastRomanLetter, int LastDecimal){
		
        if (LastRomanLetter > TotalDecimal) {
            return LastDecimal - TotalDecimal;
        } else {
            return LastDecimal + TotalDecimal;
        }
        
    }
}
