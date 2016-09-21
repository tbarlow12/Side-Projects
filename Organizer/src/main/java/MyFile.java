import java.io.File;

/**
 * Created by Tanner on 9/20/2016.
 */
public class MyFile extends File {
    private String oldPath;

    private String newPath;

    private MyDate date;

    public MyFile(String pathname) {
        super(pathname);
        oldPath = pathname;
    }

    public MyFile(File f){
        super(f.getAbsolutePath());
    }

}
