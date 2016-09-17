package org.tanner.tutorial.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.tanner.tutorial.messenger.database.DatabaseClass;
import org.tanner.tutorial.model.Profile;

public class ProfileService {

	private Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService(){
		profiles.put("tanner", new Profile(1, "tanner", "Tanner", "Barlow"));
		profiles.put("brock", new Profile(1, "brock", "Brock", "Barlow"));
		profiles.put("dustin", new Profile(1, "dustin", "Dustin", "Barlow"));
		profiles.put("nathan", new Profile(1, "nathan", "Nathan", "Barlow"));
		profiles.put("kate", new Profile(1, "kate", "Kate", "Barlow"));

	}
	
	public List<Profile> getAllProfiles(){
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName){
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile){
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile){
		if(profile.getProfileName().isEmpty()){
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile removeProfile(String profileName){
		return profiles.remove(profileName);
	}
}
