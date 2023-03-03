package id.kawahedukasi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtil {
    public static Boolean isEmail(String input){
        Pattern pattern = Pattern.compile("[A-Za-z0-9]*@[A-Za-z]*.[A-Za-z]");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
