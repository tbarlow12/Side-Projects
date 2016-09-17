package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArchiveFile implements Model{
	
	private String 				title;
	private int 				id;
	private Author 				author;
	private FileType 			type;
	private Map<String,Tag> 	tags;
	private File 				attached;
	private String 				comments = "";
	
	public ArchiveFile(String title, int id){
		this.title = title;
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	
	
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	public FileType getType() {
		return type;
	}
	public void setType(FileType type) {
		this.type = type;
	}
	
	
	
	public void addTag(Tag tag) {
		if(tags == null)
			tags = new HashMap<String,Tag>();
		tags.put(tag.getTitle(), tag);
	}
	public Map<String,Tag> getTags() {
		return tags;
	}
	public void setTags(Map<String,Tag> tags) {
		this.tags = tags;
	}
	
	
	
	public void setAttachedFile(File attached) {
		this.attached = attached;		
	}
	public File getAttachedFile(){
		return attached;
	}
	
	
	public boolean equals(ArchiveFile file){
		if(this.title.equals(file.getTitle()) && this.id == file.getId()){
			return true;
		}
		return false;
	}
	
	public String printToFile(){
		String authorTitle = "";
		String typeTitle = "";
		String attachedPath = "";
		if(author != null)
			authorTitle = author.getTitle();
		if(type != null)
			typeTitle = type.getTitle();
		if(attached != null)
			attachedPath = attached.getAbsolutePath();
		if(comments == null)
			comments = "";
		String result = id + "\t" + title + "\t" + authorTitle + "\t" + typeTitle + "\t";
		if(tags != null && tags.values().size() > 0){
			for(Tag tag : tags.values())
				result = result + tag.getTitle() + ",";
			result = result.substring(0, result.length() - 1) + "\t";
		}
		else{
			result = result + "" + "\t";
		}
		result = result + attachedPath + "\t" + comments;
		return result;
	}
	
	public String toString(){
		String s = id + ". " + title;
		if(author != null)
			s = s + " by " + author.getTitle();
		return s;
	}
	
	public String getTagsString(){
		String result = "";
		if(tags == null)
			return result;
		for(Tag tag : tags.values()){
			result = result + tag.getTitle() + ", ";
		}
		if(result.length() > 1)
			result = result.substring(0, result.length() - 2);
		return result;
	}

	public String getAttachedName(boolean full) {
		String result = "";
		if(attached != null){
			if(full)
				result = attached.getAbsolutePath();
			else
				result = attached.getName();
		}
			return result;
	}

}
