/**
 * Created by PattersonGA on 2/16/2017.
 */
public class Utilities {


    public static boolean verifyPathFormat(String str){
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c) && !(c == '\\'))
                return false;
        }
        return true;
    }

    public static boolean verifyFileFormat(String str){
        if (str ==null){
            System.out.println("The string is null");
            return false;
        }
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c))
                return false;
        }
        return true;
    }
}
