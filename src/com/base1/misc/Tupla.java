package com.base1.misc;
/**
 * Una exelente clase que reperesenta una n-tupla en java, una vez creada jamas modificada
 * @author Exequiel
 *
 */
public class Tupla{
	private String[] strings;
	public Tupla(String ...strings){
		this.strings = strings;
	}
	
	public String get(int index){
		return strings[index];
	}
}