/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Milan
 */
public class Util {

    private Properties properties;
    private static Util INSTANCE;
    private boolean status;
    
    private Util() throws IOException{
        properties = new Properties();
        properties.load(new FileInputStream(new File("db.properties")));
    }
    
    public static Util getInstance() throws IOException{
        if(INSTANCE == null){
            INSTANCE = new Util();
        }
        return INSTANCE;
    }

    public Properties getProperties() {
        return properties;
    }
    
    public String getDriver(){
        return properties.getProperty(properties.getProperty("current")+"_driver");
    }
    public String getUrl(){
        return properties.getProperty(properties.getProperty("current")+"_url");
    }
    public String getUser(){
        return properties.getProperty(properties.getProperty("current")+"_user");
    }
    public String getPassword(){
        return properties.getProperty(properties.getProperty("current")+"_password");
    }

    public boolean isConnectedStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
