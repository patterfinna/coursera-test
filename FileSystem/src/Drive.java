import java.util.ArrayList;

/**
 * Created by PattersonGA on 2/14/2017.
 */
public class Drive{
    private String name;
    private String path;
    private int size;
    private ArrayList folders = new ArrayList();
    private ArrayList files = new ArrayList();
    private ArrayList zipFiles = new ArrayList();
    private ArrayList items = new ArrayList();


    /**
     * Drive
     * @param driveName - name for this drive.
     */
    public Drive(String driveName){
        name = driveName;
        path = name + "\\";
        size = 0;
    }

    //Class Utilities
    public Object findFile(String type, String fileName){
        String str;

        if (type.equals("folder")){
            for (int i = 0;i< getNumFolders();i++){
                str = getFolder(i).getName();

                if (getFolder(i).getName().equals(fileName)){
                    return getFolder(i);
                }
            }
        }
        return null;
    }

    public Folder findFolder(String mypath){
        String [] items = mypath.split("\\\\");
        int levels = items.length;
        System.out.println("Path: " + mypath + ", Levels: " + levels);
        if (levels == 1){ return null; }
        Folder currentFolder = (Folder) findFile("folder",items[1]);
        Folder parentFolder = currentFolder;
        if (currentFolder == null) {
            System.out.println("Couldn't find folder: " + items[1]);

            return currentFolder;
        }
        System.out.println("Current Folder: " + currentFolder.toString());
        for (int i = 2;i<levels;i++){
            currentFolder = (Folder) parentFolder.findFile("folder",items[i]);
            if (currentFolder == null) {
                System.out.println("Folder in path doesn't exist");
                return currentFolder;
            }
            System.out.println("Current Folder: " + currentFolder.toString());
            parentFolder = currentFolder;
        }
        return parentFolder;
    }

    /**
     *
     * @param type - Folder, File or zipfile
     * @param newName
     * @param myPath - path including drive that goes to the folder where the item will be added
     * @return returns the size of the added item (for file in this case)
     */
    public void newFolder(String type, String newName, String myPath){
        System.out.println("Path: " + path + ", newName: " +  newName + ", type: " + type);
        Folder currentFolder = null;
        Folder parentFolder = currentFolder;
        String [] pathParts = myPath.split("\\\\");
        int levels = pathParts.length;
        System.out.println("Checking Path: " + path);
        if (levels == 1) {
            if (type.equals("folder")){
                System.out.println("newName: " + newName + ", path: " + pathParts[0]);
                Folder newFolder = new Folder(newName,pathParts[0]);
                System.out.println("Name: " + newFolder.getName());
                System.out.println("Path: " + newFolder.getPath());
                System.out.println("Size: " + newFolder.getSize());
                addFolder(newFolder);
            }
            else if (type.equals("zipFile")){
                //Todo - implement zipFile
            }
        }

        Folder f = findFolder(myPath);
        if (f != null){
            //Make sure object doesn't exist yet - no duplicates
            Object o = f.findFile(type,newName);
            if (o == null){
                if (type.equals("folder")){
                    System.out.println("newName: " + newName + ", path: " + myPath);
                    Folder newFolder = new Folder(newName,pathParts[0]);
                    System.out.println("Name: " + newFolder.getName());
                    System.out.println("Path: " + newFolder.getPath());
                    System.out.println("Size: " + newFolder.getSize());
                    f.addFolder(newFolder);
                }
                else if (type.equals("zipFile")){
                    //Todo - implement zipFile
                }
            }
        }
    }

    /**
     *
     * @param type - Folder, File or zipfile
     * @param newName
     * @param myPath - path including drive that goes to the folder where the item will be added
     * @return returns the size of the added item (for file in this case)
     */
    public int newFile(String type, String newName, String myPath, String content){
        System.out.println("Path: " + myPath + ", newName: " +  newName + ", type: " + type + ", content: " + content);
        int fileSize = content.length();
        String [] pathParts = myPath.split("\\\\");
        int levels = pathParts.length;
        if (levels == 1) {
            System.out.println("newName: " + newName + ", path: " + pathParts[0]);
            File file = new File(pathParts[0],newName,content);
            System.out.println("Name: " + file.getName());
            System.out.println("Path: " + file.getPath());
            System.out.println("Size: " + file.getSize());
            addFile(file);
            updateParentSizes(myPath,content.length());
        }
        else {
            Folder f = findFolder(myPath);
            if (f != null) {
                //Make sure object doesn't exist yet - no duplicates
                File file = (File) f.findFile("File", newName);
                if (file == null) {
                    System.out.println("Creating file with newName: " + newName + ", path: " + myPath);
                    File aFile = new File(myPath,newName,content);
                    System.out.println("Name: " + aFile.getName());
                    System.out.println("Path: " + aFile.getPath());
                    System.out.println("Size: " + aFile.getSize());
                    f.addFile(aFile);
                    updateParentSizes(myPath,content.length());
                }
            }
        }
        return fileSize;
    }

    public void updateParentSizes(String mypath, int modSize){
        System.out.println("Updating parent sizes for path: " + "mypath with size" + modSize);
        String [] items = mypath.split("\\\\");
        int levels = items.length;
        // Set drive size
        setSize(modSize);
        System.out.println("Path levels " +


                levels);
        if (levels > 1){
            //Folder currentFolder = null;
            Folder currentFolder = (Folder) findFile("folder",items[1]);
            currentFolder.setSize(modSize);
            System.out.println("Current folder " + currentFolder.getName() + " size is " + currentFolder.getSize());
            Folder parentFolder = currentFolder;
            for (int i=2;i<levels;i++){
                currentFolder = (Folder) parentFolder.findFile("folder",items[i]);
                System.out.println("Current folder " + currentFolder.getName() + " size is " + currentFolder.getSize());
                currentFolder.setSize(modSize);
                parentFolder = currentFolder;
            }
        }
    }

    public boolean deleteItem(String itemType, String itemName){
        return true;
    }

    public String getName(){
        return name;
    }

    public int getSize(){
        return size;
    }

    public void setSize(int addedSize){
        size = size + addedSize;
    }

    public String getPath(){
        return path;
    }

    public void addFolder(Folder newFolder){
        folders.add(newFolder);
    };

    public Folder getFolder(int index){
        return (Folder) folders.get(index);
    }

    public int getNumFolders(){
        return folders.size();
    }

    public void addFile(File newFile){
        files.add(newFile);
    };

    public File getFile(int index){
        return (File) files.get(index);
    }

    public int getNumFiles(){
        return files.size();
    }

}
