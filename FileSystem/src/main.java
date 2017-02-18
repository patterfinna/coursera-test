import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by PattersonGA on 2/14/2017.
 */
public class main {

    private ArrayList<Drive> drives = new ArrayList<Drive>();

    public void main(String args[]){
        //Variables
        boolean runProgram = true;

        Folder bob = new Folder("bob","a\\");
        System.out.println("Name: " + bob.getName() + ", path: " + bob.getPath());




        //Main Menu
        while(runProgram){
            System.out.println("Type 'help' to get list of commands. ");
            System.out.print("Enter command > ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                String input = br.readLine();
                String [] tokens = input.split(" ");
                //System.out.println("First string: " + tokens[0]);
                //System.out.println("Number of strings: " + tokens.length);
                //if (tokens.length > 1) System.out.println("2nd string: " + tokens[1]);


                if (tokens[0].equals("help")){ getHelp();}
                else if (tokens[0].equals("create")) {
                    if (tokens.length < 3){
                        System.out.println("Wrong number of parameters.");
                        System.out.println("create [filetype:drive|folder|file|zipfile] [name:alphanumeric] [path:of parent 'for all but drives'] [content:string 'for files only']");
                    }
                    if (tokens[1].equals("drive")){
                        if (findDrive(drives, tokens[1]) != -1){
                            System.out.println("Drive " + tokens[1] + " already exists.");
                        }
                        else {
                            Drive newDrive = new Drive(tokens[2]);
                            int driveSize = drives.size();
                            System.out.println("Drive size" + driveSize);
                            drives.add(newDrive);
                            driveSize = drives.size();
                            System.out.println("New Drive size" + driveSize);

                        }
                    }
                    else if (tokens[1].equals("folder") || tokens[1].equals("file") || tokens[1].equals("zipfile")){
                        //Find path to add folder
                        if (tokens.length < 4){
                            System.out.println("Wrong number of parameters to create a file/folder.");
                            System.out.println("create [filetype:drive|folder|file|zipfile] [name:alphanumeric] [path:of parent 'for all but drives'] [content:string 'for files only']");

                        }
                        else {
                            System.out.println("Checking Path: " + tokens[3]);
                            String[] pathParts = tokens[3].split("\\\\");
                            System.out.println("Path length: " + pathParts.length);
                            System.out.println("First Section: " + pathParts[0]);

                            //int levels = pathParts.length;
                            //System.out.println("Number of levels: " + levels);

                            int driveNum = findDrive(drives, pathParts[0]);
                            if (driveNum == -1) {
                                System.out.println("Drive " + pathParts[0] + " doesn't exist.");
                            } else {
                                Drive drive = drives.get(driveNum);
                                if (tokens[1].equals("file")){
                                    drive.newFile(tokens[1], tokens[2], tokens[3],tokens[4]);
                                }
                                else {
                                    drive.newFolder(tokens[1], tokens[2], tokens[3]);
                                }
                            }
                        }
                                            }
                    else {
                        System.out.println("Invalid create type.");
                    }

                }
                else if (tokens[0].equals("delete")) {
                    System.out.println("delete");
                }
                else if (tokens[0].equals("move")) {
                    System.out.println("move");
                }
                else if (tokens[0].equals("writetofile")) {
                    System.out.println("writetofile");
                }
                else if (tokens[0].equals("size")) {
                    System.out.println("size");
                }
                else if (tokens[0].equals("dir")) {
                    if (tokens.length == 1){
                        listDrives();
                    }
                    else if (tokens.length == 2){
                        listDrive(tokens[1]);


                    }
                    else {
                        System.out.println("Too many arguements");
                    }
                }
                else if (tokens[0].equals("quit")) {
                    runProgram = false;
                    System.out.println("Exiting program now.");
                }
                else {
                    //System.out.println("Invalid command.  Type 'help' to get a list of commands.");
                    System.out.println("Invalid command.");
                }
                System.out.println("-------");
                System.out.println("");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static int findDrive(ArrayList<Drive> aList, String name){
        String str;

        if (!Utilities.verifyFileFormat(name)){
            System.out.println("Name is not following standard.  Needs to consist of numbers and letters only.");
            return -1;
        }
        System.out.println("List Size: " + aList.size());
        for (int i = 0; i < aList.size(); i++) {
            str = aList.get(i).getName();
            System.out.println("Current String: " + name);
            if (str.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static void getHelp(){
        System.out.println("File System Commands:");
        System.out.println("create [filetype:drive|folder|file|zipfile] [name:alphanumeric] [path:of parent 'for all but drives'] [content:string 'for files only']");
        System.out.println("delete [path of element]");
        System.out.println("move [original path] [destination path]");
        System.out.println("writetofile [path to file] [content:string to replace old content]");
        System.out.println("size [path to filetype]");
        System.out.println("dir [path: context to display all of the elements - 'leave blank to list drives']");
        System.out.println("quit - exit program");

    }

    public void listDrives(){
        System.out.println("Number of Drives:  " + drives.size());
        for (int i =0; i < drives.size(); i++){
            System.out.print(drives.get(i).getName() + " ");
            System.out.print(drives.get(i).getSize() + " ");
            System.out.println(drives.get(i).getPath());
        }
    }

    public void listDrive(String path){
        //Parse path to find folder
        System.out.println("Path: " + path);
        System.out.println();
        String [] items = path.split("\\\\");
        int driveNum = findDrive(drives,items[0]);
        if (driveNum == -1){
            System.out.println("Drive doesn't exist");
            pass = false;
        }
        else {
            Drive d = drives.get(driveNum);
            System.out.println("Drive: " + d.toString());

            //d.findPath("")
            //print folders
            if (d != null) {
                if (items.length == 1) {
                    for (int i = 0; i < d.getNumFolders(); i++) {
                        System.out.print(d.getFolder(i).getName() + " ");
                        System.out.print(d.getFolder(i).getSize() + " ");
                        System.out.println(d.getFolder(i).getPath());
                    }
                    for (int i = 0; i < d.getNumFiles(); i++) {
                        System.out.print(d.getFile(i).getName() + " ");
                        System.out.print(d.getFile(i).getSize() + " ");
                        System.out.println(d.getFile(i).getPath());
                    }
                } else {
                    Folder f = d.findFolder(tokens[1]);
                    if (f == null){
                        System.out.println("Folder doesn't exist");
                        pass = false;
                    }
                    else {
                        //show contents
                        for (int i = 0; i < f.getNumFolders(); i++) {
                            System.out.print(f.getFolder(i).getName() + " ");
                            System.out.print(f.getFolder(i).getSize() + " ");
                            System.out.println(f.getFolder(i).getPath());
                        }
                        for (int i = 0; i < f.getNumFiles(); i++) {
                            System.out.print(f.getFile(i).getName() + " ");
                            System.out.print(f.getFile(i).getSize() + " ");
                            System.out.println(f.getFile(i).getPath());
                        }
                    }
                }
            }
        }
    }
}
