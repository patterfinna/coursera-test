import java.util.ArrayList;

/**
 * Created by PattersonGA on 2/14/2017.
 */
public class Folder extends Drive {
    private String folderName;
    private String folderPath;
    private int folderSize;
    private ArrayList folders = new ArrayList();
    private ArrayList files = new ArrayList();
    private ArrayList zipFiles = new ArrayList();

    public Folder(String name, String path){
        folderName = name;
        folderPath = path;
        folderSize = 0;
    }

    public String getName(){ return folderName; }
    public String getPath(){ return folderPath; }
    public int getSize(){ return folderSize; }
    public void setSize(int newSize){ folderSize = folderSize + newSize; }
}
