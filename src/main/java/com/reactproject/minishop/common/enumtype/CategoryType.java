package com.reactproject.minishop.common.enumtype;


/*
 * 0 : 하의 28,30,32,34....
 * 1 : 상의 s,m,l,xl,xxl
 * 2 : sizefree : 단순 stock
 * 
 * */
public enum CategoryType {
	BOTTOMS(0,"bottoms"), SHIRTS(1,"shirts"), TEES(1,"tees"),
	FLEECE(1,"fleece"), KNITS(1,"knits"), OUTERWEAR(1,"outerwear"), 
	FOOTWEAR(2,"footwear"),ACCESSORIES(2,"accessories"), 
	LIFESTYLE(2,"lifestyle"), HOME(2,"home");
	
	private CategoryType(int code, String name) {
		
	}
	
	private String name;
	private int code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
}
