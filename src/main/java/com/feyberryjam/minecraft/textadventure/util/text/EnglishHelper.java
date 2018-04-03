package com.feyberryjam.minecraft.textadventure.util.text;

// here we have my ridiculous and probably totally inappropriate way
// of handling a's / an's

// this will return "an ocean" "the end" or "a hell"

public class EnglishHelper 
{
	public static String prefixWithAorAn(String base)
	{
		String returnString = base;

		
		if( base.toLowerCase().startsWith("the"))
			return base;
		
		String vowels = "aeiou";
		if (vowels.indexOf(Character.toLowerCase(returnString.charAt(0))) != -1) 
		{
			// If starts with vowel...
			returnString = "an " + returnString;
		}		
		else
			returnString = "a " + returnString;			

		return returnString;
	}
}
