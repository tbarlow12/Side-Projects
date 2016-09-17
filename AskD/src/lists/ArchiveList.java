package lists;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import model.ArchiveFile;
import model.Author;
import model.Database;
import model.FileType;
import model.Model;
import model.Tag;

public class ArchiveList {

	Database database;
	ArrayList<ArchiveFile> allFiles;
	ArrayList<String> allTitles;
	ArrayList<Author> allAuthors;
	ArrayList<Tag> allTags;
	ArrayList<FileType> allTypes;
	
	String titleConstraint = "";
	String authorConstraint = "";
	String tagConstraint = "";
	String typeConstraint = "";	
	
	public ArchiveList(String pathname){
		
		database = new Database(pathname);
		
		allFiles = database.getFilesListById();
		allTitles = database.getTitleListByTitle();
		allAuthors = database.getAuthorListByTitle();
		allTags = database.getTagListByTitle();
		allTypes = database.getTypeListByTitle();
	}
	
	public ArrayList<ArchiveFile> getAllFiles() {
		return allFiles;
	}

	public ArrayList<String> getAllTitles() {
		return allTitles;
	}

	public ArrayList<Author> getAllAuthors() {
		return allAuthors;
	}

	public ArrayList<Tag> getAllTags() {
		return allTags;
	}

	public ArrayList<FileType> getAllTypes() {
		return allTypes;
	}

	public void setTitleConstraint(String titleConstraint) {
		this.titleConstraint = titleConstraint.toLowerCase();
	}

	public void setAuthorConstraint(String authorConstraint) {
		this.authorConstraint = authorConstraint.toLowerCase();
	}

	public void setTagConstraint(String tagConstraint) {
		this.tagConstraint = tagConstraint.toLowerCase();
	}

	public void setTypeConstraint(String typeConstraint) {
		this.typeConstraint = typeConstraint.toLowerCase();
	}
	
	public ArrayList<ArchiveFile> select(Comparator<Model> comparator){
		ArrayList<ArchiveFile> files = new ArrayList<ArchiveFile>();
		for(ArchiveFile file : allFiles){
			String title, author = "", type = "";
			boolean containsTag = false;
			title = file.getTitle().toLowerCase();
			Integer id = file.getId();
			if(file.getAuthor() != null)
				author = file.getAuthor().toString().toLowerCase();
			Map<String,Tag> tags = file.getTags();
			if(tags != null)
				containsTag = containsTagRestraint(tagConstraint, tags);
			if(file.getType() != null)
				type = file.getType().toString().toLowerCase();
			if((title.contains(titleConstraint) || titleConstraint.equals("")) &&
			(author.contains(authorConstraint) || authorConstraint.equals("")) &&
			(containsTag || tagConstraint.equals("")) &&
			(type.contains(typeConstraint) || typeConstraint.equals(""))){
				files.add(file);	
			}
		}
		Collections.sort(files, comparator);
		return files;
	}
	
	public void addFile(ArchiveFile file, boolean replace){
		database.addFile(file, replace);
		update();
	}
	
	public void addAuthor(String author, ArchiveFile file){
		database.addAuthor(author, file);
	}
	
	public void addType(String type, ArchiveFile file){
		database.addType(type, file);
	}
	
	public void addTags(String tags, ArchiveFile file){
		database.addTags(tags, file);
	}


	private boolean containsTagRestraint(String constraint, Map<String, Tag> tags) {
		if(tags == null)
			return false;
		for(String key : tags.keySet())
			if(key.toLowerCase().contains(constraint.toLowerCase()))
				return true;
		return false;
	}
	
	public void update(){
		allFiles = database.getFilesListById();
		allTitles = database.getTitleListByTitle();
		allAuthors = database.getAuthorListByTitle();
		allTags = database.getTagListByTitle();
		allTypes = database.getTypeListByTitle();
	}

	public boolean contains(String s) {
		for(ArchiveFile file : allFiles){
			if(s.equalsIgnoreCase(file.getTitle()))
				return true;
		}
		return false;
	}

	public boolean contains(Integer id) {
		for(ArchiveFile file : allFiles){
			if(id.equals(file.getId()))
				return true;
		}
		return false;
	}

	public void updateFile(ArchiveFile file, String author, String type, String tags, String comments, String fileToAttach) {
		
		if(!author.isEmpty())
			database.addAuthor(author, file);
		if(!type.isEmpty())
			database.addType(type, file);
		if(!tags.isEmpty())
			database.addTags(tags, file);
		if(!comments.isEmpty())
			database.setComments(comments, file);
		if(fileToAttach != null && !fileToAttach.isEmpty()){
			File attach = new File(fileToAttach);
			if(attach.isFile() && attach.exists())
				file.setAttachedFile(attach);
		}
		update();
		database.writeArchivesToFile(database.getPathname());
	}

	public void readToDatabase(String path) {
		database.readArchivesFromFile(path);
		update();
	}

	public void writeToFile(String path) {
		database.writeArchivesToFile(path);
		update();
	}

	public void deleteFile(String fileName) {
		database.deleteFile(fileName);
		database.writeArchivesToFile(database.getPathname());
		update();
	}

}
