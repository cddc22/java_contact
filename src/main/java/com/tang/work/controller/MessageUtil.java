package com.tang.work.controller;

import org.springframework.util.ObjectUtils;
import java.util.regex.Pattern;

public class MessageUtil {
    public static boolean RegisterMessageByPassword(String s){
        if(s.length()>30||s.length()<6||ObjectUtils.isEmpty(s)||s.replace(" ","").equals(""))
            return false;
        else
            return true;
    }
    public static boolean RegisterMessageByNumber(String s){
        Pattern pattern=Pattern.compile("[0-9]*");
        if(ObjectUtils.isEmpty(s)||s.replace(" ","").equals("")||s.length()!=12||!pattern.matcher(s).matches())
            return false;
        else
            return true;
    }
    public static boolean RegisterMessage(String s){
        Pattern pattern=Pattern.compile("[0-9]*");
        if(ObjectUtils.isEmpty(s)||s.replace(" ","").equals("")||Integer.parseInt(s)>100||Integer.parseInt(s)<0||!pattern.matcher(s).matches())
            return false;
        else
            return true;
    }

}
