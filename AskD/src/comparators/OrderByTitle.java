package comparators;

import java.util.Comparator;

import model.ArchiveFile;
import model.Model;

public class OrderByTitle implements Comparator<Model> {

	/**
     * Returns a negative value if lhs is smaller than rhs. Returns a positive
     * value if lhs is larger than rhs. Returns 0 if lhs and rhs are equal.
     */
	
	@Override
	public int compare(Model o1, Model o2) {
		return o1.getTitle().compareToIgnoreCase(o2.getTitle());
	}

    

  }