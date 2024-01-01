package dataHandler;

import constants.LogConstants;
import exceptions.AutomationTestRunException;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationPropertiesReader {

    private static final Logger logger = Logger.getLogger(ConfigurationPropertiesReader.class);

    private static ConfigurationPropertiesReader instance;
    private static Properties properties;

    public ConfigurationPropertiesReader(String configFilePath) {
        properties = getProperties(configFilePath);
    }

    public static ConfigurationPropertiesReader getInstance() {
        if (instance == null) {
            instance = new ConfigurationPropertiesReader("src/main/resources/Configuration.properties");
        }
        return instance;
    }

    public String getProperty(final String keyName) {
        String value = properties.getProperty(keyName);
        if (value == null) {
            logger.warn(LogConstants.TARGET_PROPERTIES_NULL + keyName);
        }
        return value;
    }

    private Properties getProperties(final String fileName) {

        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/Configuration.properties");
            properties.load(fis);
        } catch (FileNotFoundException e) {
            throw new AutomationTestRunException(LogConstants.FILE_NOT_FOUND, e);
        } catch (IOException e) {
            throw new AutomationTestRunException(LogConstants.CANNOT_GET_PROPERTIES_FROM_FILE, e);
        }
        return properties;
    }

}
