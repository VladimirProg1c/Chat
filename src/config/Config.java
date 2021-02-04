package config;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";

    public static Properties properties = new Properties();
    //public static Properties properties;
    public synchronized static String getProperty(String name){
        //public Properties properties = new Properties();

        if (properties.isEmpty()){
            try (InputStream is = Config.class.getClassLoader().getResourceAsStream("config.properties")){

                properties.load(is);
            }
            catch (Exception ex){
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
        return properties.getProperty(name);

    }
}
