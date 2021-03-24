/**
 * 
 */
package com.galaxy.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;


public class ValidacionesEntrada {

	private Scanner scan;
	private LineaEntradas conversationLine;
	private MensajesError eMessage;
	
	private HashMap<String, String> constantAssignments;
	
	
	private HashMap<String, String> computedLiterals;
	
	
	
	private ArrayList<String> output;
	
	
	
	public ValidacionesEntrada()
	{
		this.scan = new Scanner(System.in);
		this.conversationLine = new LineaEntradas();
		this.eMessage = new MensajesError();
		this.constantAssignments = new HashMap<String, String>();
		this.computedLiterals = new  HashMap<String, String>();
		this.output = new ArrayList<String>();
	}
	
	
	
	
	public ArrayList<String> read()
	{
		String line;
		int count=0;
		CodigosError error = null;
		
		
		while(this.scan.hasNextLine() && (line = this.scan.nextLine()).length()>0 )
		{
			error = validate(line);
			
			switch(error)
			{
				case NO_IDEA :  this.output.add(this.eMessage.getMessage(error));break;
				
				default : this.eMessage.printMessage(error);
			}
			
			count++;
		}
		
		switch(count)
		{
			case 0: error = CodigosError.NO_INPUT;
					this.eMessage.printMessage(error);
					break;
					
			default : 
		}
		
		return this.output;
		
	}
	
	

	
	private CodigosError validate(String line)
	{
		
		CodigosError error = CodigosError.SUCCESS_OK;
		
		LineaEntradas.Type lineType = this.conversationLine.getLineType(line);
		
		switch(lineType)
		{
			case ASSIGNED : 		 processAssignmentLine(line);
							         break;
							
			case CREDITS :			 processCreditsLine(line);
						    		 break;
						    
			case QUESTION_HOW_MUCH : processHowMuchQuestion(line);
									 break;
									 
			case QUESTION_HOW_MANY : processHowManyCreditsQuestion(line);
									 break;
			
			default : error = CodigosError.NO_IDEA; break;
		}
				
		return error;
	}
	
	
	
	
	
	
	private void processAssignmentLine(String line)
	{
		
		String[] splited = line.trim().split("\\s+");
		
		
		try
		{
			
			constantAssignments.put(splited[0], splited[2]);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			this.eMessage.printMessage(CodigosError.INCORRECT_LINE_TYPE);
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	private void processHowMuchQuestion(String line)
	{
		try
		{
			
			
			String formatted = line.split("\\sis\\s")[1].trim();
			
			
			formatted = formatted.replace("?","").trim();
			
			
			String keys[] = formatted.split("\\s+");
			
			
			String romanResult="";
			String completeResult = null;
			boolean errorOccured = false;
			
			for(String key : keys)
			{
				
				String romanValue = constantAssignments.get(key);
				if(romanValue==null)
				{
					
					completeResult = this.eMessage.getMessage(CodigosError.NO_IDEA);
					errorOccured = true;
					break;
				}
				romanResult += romanValue;
			}
			
			if(!errorOccured)
			{
				
				romanResult = NumeroRomanos.romanToArabic(romanResult);
				completeResult = formatted+" is "+romanResult;
			}
				
			output.add(completeResult);
			
		}
		catch(Exception e)
		{
			this.eMessage.printMessage(CodigosError.INCORRECT_LINE_TYPE);
			System.out.println(e.getMessage());
			
		}
	}
	

	private void processCreditsLine(String line)
	{
		try
		{
			
			String formatted = line.replaceAll("(is\\s+)|([c|C]redits\\s*)","").trim();
			
			
			String[] keys = formatted.split("\\s");
			
			
			String toBeComputed = keys[keys.length-2];
			float value = Float.parseFloat(keys[keys.length-1]);
			
			
			
			
			String roman="";
			
			for(int i=0;i<keys.length-2;i++)
			{
				roman += constantAssignments.get(keys[i]);
			}
			
			int romanNumber = Integer.parseInt(NumeroRomanos.romanToArabic(roman));
			float credit = (float)(value/romanNumber);
			
					
			computedLiterals.put(toBeComputed, credit+"");
		}
		catch(Exception e)
		{
			
			this.eMessage.printMessage(CodigosError.INCORRECT_LINE_TYPE);
			System.out.println(e.getMessage());
			
		}
	}
	
	

	private void processHowManyCreditsQuestion(String line) {
		
		try
		{
			
			String formatted = line.split("(\\sis\\s)")[1];
			
			formatted = formatted.replace("?","").trim();
			
			
			String[] keys = formatted.split("\\s");
			
			boolean found = false;
			String roman = "";
			String outputResult = null;
			Stack<Float> cvalues = new Stack<Float>();
			
			for(String key : keys)
			{
				found = false;
				
				String romanValue = constantAssignments.get(key);
				if(romanValue!=null)
				{
					roman += romanValue;
					found = true;
				}
				
				String computedValue = computedLiterals.get(key);
				if(!found && computedValue!=null)
				{
					cvalues.push(Float.parseFloat(computedValue));
					found = true;
				}
				
				if(!found)
				{
					outputResult = this.eMessage.getMessage(CodigosError.NO_IDEA);
					break;
				}
			}
			
			if(found)
			{
				float res=1;
				for(int i =0;i<cvalues.size();i++)
				res *= cvalues.get(i);
					
				int finalres= (int) res;
				if(roman.length()>0)
				finalres = (int)(Integer.parseInt(NumeroRomanos.romanToArabic(roman))*res);
				outputResult = formatted +" is "+ finalres +" Credits";
			}
			
			this.output.add(outputResult);
			
		}
		catch(Exception e)
		{
			this.eMessage.printMessage(CodigosError.INCORRECT_LINE_TYPE);
			System.out.println(e.getMessage());
		}
		
	}
}
