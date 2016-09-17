package test;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import model.*;

public class TestDatabase {

	public static void main(String[] args) {
		Database database = new Database("TestData.txt");
		Map<String, ArchiveFile> files = database.getFiles();
		
		for(String key : files.keySet()){
			ArchiveFile arch = files.get(key);
			System.out.println(arch.toString());
		}
	}

}
