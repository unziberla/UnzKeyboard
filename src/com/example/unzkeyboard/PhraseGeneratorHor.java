package com.example.unzkeyboard;

import java.util.Vector;

public class PhraseGeneratorHor extends PhraseGenerator {
	private Vector<Vector<String> > phrases;
	private int index, set;
	
	public PhraseGeneratorHor() {
		phrases = new Vector<Vector<String>>();
		index = set = 0;
		for (int i=0; i<5; i++)
		{
			phrases.add(new Vector<String>());
		}
		
		phrases.get(0).add("0. Type the following phrases");		
		
		phrases.get(0).add("video camera with a zoom lens, this is a very good idea.");		
		phrases.get(0).add("a steep learning curve in riding a unicycle");
		phrases.get(0).add("the first time he tried to swim, he almost flooded");			
		phrases.get(0).add("important news always seems to be late");						
		phrases.get(0).add("I put garbage in an abandoned mine");							
		phrases.get(0).add("Mario, you are a capitalist pig");								
		phrases.get(0).add("dormitory doors are locked at midnight");
		phrases.get(0).add("if you come home late, the doors are locked");					
		phrases.get(0).add("most judges are very honest");
		
		
		phrases.get(1).add("1. Type the following phrases at the maximum speed you can achieve\n don't care too much of errors!");				

		phrases.get(1).add("the insulation is not working");
		phrases.get(1).add("a steep learning curve in riding a unicycle");
		phrases.get(1).add("Mario, you are a capitalist pig");		
		phrases.get(1).add("meet tomorrow in the lavatory");
		phrases.get(1).add("if you come home late, the doors are locked");		
		phrases.get(1).add("I put garbage in an abandoned mine");	
		phrases.get(1).add("the first time he tried to swim, he almost flooded");			
		phrases.get(1).add("important news always seems to be late");	
		phrases.get(1).add("the fire raged for an entire month");
		phrases.get(1).add("video camera with a zoom lens, this is a very good idea.");		
		
		
		phrases.get(2).add("2. Type the following phrases while walking");				

		phrases.get(2).add("video camera with a zoom lens, this is a very good idea.");		
		phrases.get(2).add("our life expectancy has increased");
		phrases.get(2).add("if you come home late, the doors are locked");		
		phrases.get(2).add("I put garbage in an abandoned mine");		
		phrases.get(2).add("the location of the crime");
		phrases.get(2).add("important news always seems to be late");		
		phrases.get(2).add("rectangular objects have four sides");
		phrases.get(2).add("a steep learning curve in riding a unicycle");
		phrases.get(2).add("the first time he tried to swim, he almost flooded");	
		phrases.get(2).add("Mario, you are a capitalist pig");		
		
		
		phrases.get(3).add("3. Type the following phrases without watching");				

		phrases.get(3).add("bring the offenders to justice");
		phrases.get(3).add("a steep learning curve in riding a unicycle");
		phrases.get(3).add("important news always seems to be late");	
		phrases.get(3).add("the first time he tried to swim, he almost flooded");
		phrases.get(3).add("I put garbage in an abandoned mine");	
		phrases.get(3).add("give me one spoonful of coffee");
		phrases.get(3).add("if you come home late, the doors are locked");	
		phrases.get(3).add("Mario, you are a capitalist pig");	
		phrases.get(3).add("Thank you so much for participating in this");
		
		
	}			
				
	public String getPhrase() {
		String s;
		if (index == 10 || (index == 5 && set == 3))
		{
			index = 0;
			set++;
		}
		if (set == 4)
		{
			s = "EXPERIMENT FINSHED";
		}
		else 
		{
			s = phrases.get(set).get(index);
			index++;
		}
		return s;
	}
}