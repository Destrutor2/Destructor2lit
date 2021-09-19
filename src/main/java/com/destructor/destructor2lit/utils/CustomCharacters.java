package com.destructor.destructor2lit.utils;

import net.minecraft.server.v1_8_R3.SharedConstants;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class CustomCharacters {
	public static void ModifyAllowedCharacters() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field field = SharedConstants.class.getDeclaredField("allowedCharacters");
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField( "modifiers" );
		modifiersField.setAccessible( true );
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		String oldallowedchars = (String)field.get(null);
		String suits = "\u2665\u2666\u2663\u2660";
		StringBuilder sb = new StringBuilder();
		sb.append( oldallowedchars );
		sb.append( suits );
		field.set( null, sb.toString() );
	}
}
