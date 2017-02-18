/**
 * Created by PattersonGA on 2/14/2017.
 */
public class File {
    private String name;
    private String path;
    private int size;
    private String content;

    public File(String parentPath, String fileName, String fileContent){
        System.out.println("Creating new file with name: " + fileName + ",path: " + parentPath + ",content: " + fileContent + ",size: " + fileContent.length());
        name = fileName;
        path = parentPath;
        content = fileContent;
        size = fileContent.length();
    }

    public void setContent(String newContent){
        content = newContent;
        size = content.length();
    }
    public String getContent(){
        return content;
    }

    public String getName(){
        return name;
    }

    public int getSize(){
        return size;
    }

    public void setSize(){ size = content.length(); }

    public String getPath(){
        return path;
    }

    public String getContext() { return content; }

}
