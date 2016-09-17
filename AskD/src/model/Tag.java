package model;

import java.util.HashMap;
import java.util.Map;

public class Tag implements Model{

	private String title;
	
	public Tag(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String toString(){
		return this.title;
	}
	
	public boolean equals(Tag tag){
		return this.title.equals(tag.getTitle());
	}
}
