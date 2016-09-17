package model;

import java.util.*;

public class FileType implements Model{

	private String title;
	
	public FileType(String title){
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
	
	public boolean equals(FileType type){
		return this.title.equals(type.getTitle());
	}

}
