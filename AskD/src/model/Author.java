package model;

import java.util.*;

public class Author implements Model{

	private String title;
	
	public Author(String author){
		this.title = author;
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
	
	public boolean equals(Author author){
		return this.title.equals(author.getTitle());
	}
}
