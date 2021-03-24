/**
 * 
 */
package com.galaxy.merchant;


public class LineaEntradas{

	//Se declara variables constantes  para las asignaciones,
	//creditos, consultas y no encontradas
	public static enum Type{
		
		 
		 ASSIGNED, //Representa las asignaciones
		 
		
		 CREDITS, //los creditos en las entradas
		 
		
		 QUESTION_HOW_MUCH, //pregunta de cuanto es
		 
		 
		 QUESTION_HOW_MANY, //pregunta de cuantas es
		 
		
		 NOMATCH //no encontrado
		 
	}
	
	
	public class LineFilter
	{
		private LineaEntradas.Type type;
		private String pattern;
		public LineFilter(LineaEntradas.Type type,String pattern)
		{
			this.type = type;
			this.pattern = pattern;
		}
		
		public String getPattern()
		{
			return this.pattern;
					
		}
		
		public LineaEntradas.Type getType()
		{
			return this.type;
		}
	}
	
	
	
	//se asigna para conocer los datos ingresados para cada patron
	public static String patternAssigned = "^([A-Za-z]+) is ([I|V|X|L|C|D|M])$";
	public static String patternCredits = "^([A-Za-z]+)([A-Za-z\\s]*) is ([0-9]+) ([c|C]redits)$";
	public static String patternHowMuch = "^how much is (([A-Za-z\\s])+)\\?$";
	public static String patternHowMany= "^how many [c|C]redits is (([A-Za-z\\s])+)\\?$";
	private LineFilter[] linefilter;

	

	public LineaEntradas()
	{
		
		this.linefilter = new LineFilter[4];
		this.linefilter[0] = new LineFilter(LineaEntradas.Type.ASSIGNED, patternAssigned);
		this.linefilter[1] = new LineFilter(LineaEntradas.Type.CREDITS, patternCredits);
		this.linefilter[2] = new LineFilter(LineaEntradas.Type.QUESTION_HOW_MUCH, patternHowMuch);
		this.linefilter[3] = new LineFilter(LineaEntradas.Type.QUESTION_HOW_MANY, patternHowMany);
		
	}
		
		

	public LineaEntradas.Type getLineType(String line)
	{
		line = line.trim();
		LineaEntradas.Type result = Type.NOMATCH;
		
		boolean matched = false;
			
		for(int i =0;i<linefilter.length && !matched ;i++)
		{
			if( line.matches(linefilter[i].getPattern()) )
			{
				matched = true;
				result = linefilter[i].getType();
			}
			
		}
		
		return result;
		
	}
	
}
