package ru.redate.playmover;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ConnectionProperties {

    static private final ResourceBundle propsFile = PropertyResourceBundle.getBundle("connection");

    static public String getProperty(String key){
        return propsFile.getString(key);
    }
}
