package model;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


import askD.MainPanel;
import comparators.OrderById;
import comparators.OrderByTitle;

public class Database {
	
	private Map<String, ArchiveFile> filesMap = new HashMap<String, ArchiveFile>();
	private Map<String, Author> authorsMap = new HashMap<String, Author>();
	private Map<String, Tag> tagsMap = new HashMap<String, Tag>();
	private Map<String, FileType> typesMap = new HashMap<String, FileType>();
	
	private String pathname;
	
	private static final String HEADER = "ID" + "\t" + "Title" + "\t" + "Author" + "\t" + "Type" + "\t" + "Tags" + "\t" + "Attached" + "\t" + "Comments";
	private static final String FILE_FORMAT = "UTF-8";
	private static final Pattern ATTRIBUTE_DELIMITER = Pattern.compile("\t");
	private static final int ID_COL = 0;
	private static final int TITLE_COL = 1;
	private static final int AUTHOR_COL = 2;
	private static final int TYPE_COL = 3;
	private static final int TAGS_COL = 4;
	private static final int ATTACHED_COL = 5;
	private static final int COMMENTS_COL = 6;
	
	
	public Database(String pathname){
		this.pathname = pathname;
		readArchivesFromFile(pathname);
		if(filesMap == null || filesMap.size() == 0)
			MainPanel.setFirstLaunch();			
	}
	
	public Database(){}
	
	public void readArchivesFromFile(String pathname) {
		
		BufferedReader 		reader = null;
		String 				line;
		String 				title;
		int 				id;
		ArchiveFile			archiveFile;
		try{
			File file = new File(pathname);
			if(!file.exists()){
				file.createNewFile();
				MainPanel.showMessage("Couldn't find database file. Created new one: " + file.getAbsolutePath());
			}
			reader = new BufferedReader( new FileReader(pathname));
			
			while( reader.ready() ){
				line = reader.readLine();
				String[] attributes;
				line = removeUTF8BOM(line);
				line = line.trim();
				if(line.equals("") || line.equals(HEADER)){
					continue;
				}
				attributes = ATTRIBUTE_DELIMITER.split(line);
				if(attributes.length < 2){
					MainPanel.showMessage("Invalid data");
					return;
				}
				title = attributes[TITLE_COL];
				try{
					id = Integer.parseInt(attributes[ID_COL]);
				}catch(NumberFormatException e){
					MainPanel.showMessage("Invalid id for archive file: " + title);
					e.printStackTrace();
					return;
				}
				
				archiveFile = new ArchiveFile(title, id);
				
				if(attributes.length - 1 >= AUTHOR_COL){
					Author author;
					String authorName = attributes[AUTHOR_COL].trim();
					if(authorName.length() > 0){
						addAuthor(authorName,archiveFile);
					}
				}
				if(attributes.length - 1 >= TAGS_COL){
					String tags = attributes[TAGS_COL].trim();
					if(tags.length() > 0){
						addTags(tags, archiveFile);
					}
				}
				if(attributes.length - 1 >= TYPE_COL){
					String typeName = attributes[TYPE_COL].trim();
					if(typeName.length() > 0){
						addType(typeName, archiveFile);
					}
				}
				if(attributes.length - 1 >= ATTACHED_COL){
					String attachedName = attributes[ATTACHED_COL].trim();
					if(attachedName.length() > 0){
						File attached = new File(attachedName);
						if(!attached.exists()){
							MainPanel.showMessage("File " + attachedName + " not found for archive file: " + archiveFile.getId() +". " + archiveFile.getTitle());
						}
						archiveFile.setAttachedFile(attached);
					}
				}
				if(attributes.length - 1 >= COMMENTS_COL){
					String comments = attributes[COMMENTS_COL].trim();
					if(comments.length() > 0)
						archiveFile.setComments(comments);
				}
				else{
					//TODO Error message for incorrect label
				}
				if(archiveFile != null)
					filesMap.put(archiveFile.getTitle(), archiveFile);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addTags(String tags, ArchiveFile archiveFile) {
		String[]tagsArray = tags.split(",");
		for(int j = 0; j < tagsArray.length; j++){
			String tagTitle = tagsArray[j].trim();
			addTag(tagTitle, archiveFile);
		}
	}

	public void appendArchivesToFile(String pathname){
		List<ArchiveFile> list = getFilesListById();
		for(ArchiveFile arch : list){
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(pathname, true)))) {
			    out.println(arch.printToFile());
			}catch (IOException e) {
			}
		}
	}
	
	public void writeArchivesToFile(String pathname){
		List<ArchiveFile> list = getFilesListById();
		String result = HEADER;
		for(ArchiveFile arch : list){
			result = result + "\n" + arch.printToFile();
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(pathname));
			writer.write(result);
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private String removeUTF8BOM(String line) {
		if( line.startsWith("\uFEFF")){
			return line.substring(1);
		}
		else{
			return line;
		}
	}

	public Map<String, ArchiveFile> getFiles() {
		return filesMap;
	}
	
	public ArrayList<ArchiveFile> getFilesListById(){
		ArrayList<ArchiveFile> list = new ArrayList<ArchiveFile>();
		for(ArchiveFile file : filesMap.values()){
			list.add(file);
		}
		list.sort(new OrderById());
		return list;
	}

	public ArrayList<ArchiveFile> getFilesListByTitle(){
		ArrayList<ArchiveFile> list = new ArrayList<ArchiveFile>();
		for(ArchiveFile file: filesMap.values()){
			list.add(file);
		}
		list.sort(new OrderByTitle());
		return list;
	}
	
	public String[][] getMasterTable(){
		ArrayList<ArchiveFile> list = getFilesListById();
		String[][] result = new String[list.size()][2];
		for(int i = 0; i < list.size(); i++){
			result[i][0] = "" + list.get(i).getId();
			result[i][1] = list.get(i).getTitle();
		}
		return result;
	}
	
	public Map<String, Author> getAuthors() {
		return authorsMap;
	}
	
	public ArrayList<Author> getAuthorListByTitle(){
		ArrayList<Author> list = new ArrayList<Author>();
		for(ArchiveFile file: filesMap.values()){
			if(file.getAuthor() != null && !list.contains(file.getAuthor()))
				list.add(file.getAuthor());
		}
		list.sort(new OrderByTitle());
		return list;
	}
	
	public Map<String, Tag> getTags() {
		return tagsMap;
	}
	
	public ArrayList<Tag> getTagListByTitle(){
		ArrayList<Tag> list = new ArrayList<Tag>();
		for(ArchiveFile file: filesMap.values()){
			if(file.getTags() != null )
				for(Tag tag : file.getTags().values()){
					if(!list.contains(tag))
						list.add(tag);
				}
		}
		list.sort(new OrderByTitle());
		return list;
	}
	
	public Map<String, FileType> getTypes() {
		return typesMap;
	}
	
	public ArrayList<FileType> getTypeListByTitle(){
		ArrayList<FileType> list = new ArrayList<FileType>();
		for(ArchiveFile file: filesMap.values()){
			if(file.getType() != null && !list.contains(file.getType()))
				list.add(file.getType());
		}
		list.sort(new OrderByTitle());
		return list;
	}

	public ArrayList<String> getTitleListByTitle() {
		ArrayList<String> list = new ArrayList<String>();
		for(String title: filesMap.keySet()){
			list.add(title);
		}
		Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		return list;
	}

	public boolean containsFile(ArchiveFile file){
		return filesMap.containsKey(file.getTitle());
	}
	public boolean addFile(ArchiveFile file, boolean replace){
		if(replace){
			removeAttributes(file);
			filesMap.replace(file.getTitle(), file);
			writeArchivesToFile(pathname);
			return false;
		}else{
			filesMap.put(file.getTitle(), file);
			writeArchivesToFile(pathname);
			return true;
		}
	}
	private void removeAttributes(ArchiveFile file) {
		
		//TODO Check to see if there are other files that share the author, type or tags
		//if not, remove them
		Author author = file.getAuthor();
		for(ArchiveFile arch : filesMap.values()){
			
		}
		FileType type = file.getType();
		Map<String, Tag> tags = file.getTags();
		
		if(author != null)
			authorsMap.remove(author.getTitle());
		if(type != null)
			typesMap.remove(type.getTitle());
		if(tags != null)
			for(Tag tag : tags.values())
				tagsMap.remove(tag.getTitle());
		
	}

	public boolean containsAuthor(String author){
		return authorsMap.containsKey(author);
	}
	public boolean addAuthor(String author, ArchiveFile archiveFile){
		if(authorsMap.containsKey(author)){
			Author oldAuthor = authorsMap.get(author);
			archiveFile.setAuthor(oldAuthor);
			return false;
		}else{
			Author newAuthor = new Author(author);
			archiveFile.setAuthor(newAuthor);
			authorsMap.put(author, newAuthor);
			return true;
		}
	}
	public boolean containsTag(String tag){
		return tagsMap.containsKey(tag);
	}
	public boolean addTag(String tagTitle, ArchiveFile archiveFile){
		if(tagsMap.containsKey(tagTitle)){
			Tag tag = tagsMap.get(tagTitle);
			archiveFile.addTag(tag);
			return false;
		}else{
			Tag tag = new Tag(tagTitle);
			archiveFile.addTag(tag);
			tagsMap.put(tagTitle, tag);
			return true;
		}
	}
	public boolean containType(FileType type){
		return typesMap.containsKey(type.getTitle());
	}
	public boolean addType(String typeTitle, ArchiveFile archiveFile){
		if(typesMap.containsKey(typeTitle)){
			FileType type = typesMap.get(typeTitle);
			archiveFile.setType(type);
			return false;
		}else{
			FileType type = new FileType(typeTitle);
			archiveFile.setType(type);
			typesMap.put(typeTitle, type);
			return true;
		}
	}

	public void setComments(String comments, ArchiveFile file) {
		file.setComments(comments);
		filesMap.replace(file.getTitle(), file);
	}

	public String getPathname() {
		return pathname;
	}

	public void deleteFile(String fileName) {
		filesMap.remove(fileName);
	}

	
}
