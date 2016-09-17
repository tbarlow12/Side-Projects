package comparators;

import java.util.Comparator;

import model.ArchiveFile;

public class OrderById implements Comparator<ArchiveFile> {

    /**
     * Returns a negative value if lhs is smaller than rhs. Returns a positive
     * value if lhs is larger than rhs. Returns 0 if lhs and rhs are equal.
     */

	@Override
	public int compare(ArchiveFile arg0, ArchiveFile arg1) {
		// TODO Auto-generated method stub
		if(arg0.equals(arg1)) return 0;
		else if(arg0.getId() > arg1.getId())
			return 1;
		else
			return -1;
	}
  }

