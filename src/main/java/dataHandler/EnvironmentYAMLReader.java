package dataHandler;

import constants.LogConstants;
import enumerations.EnvironmentType;
import exceptions.AutomationTestRunException;
import exceptions.AutomationUtilException;
import model.GenericModel;
import org.apache.log4j.Logger;
import utilities.ConvertUtil;

public class EnvironmentYAMLReader {
    private static final Logger LOGGER = Logger.getLogger(EnvironmentYAMLReader.class);

    private static EnvironmentYAMLReader instance;
    private static GenericModel environmentGenericModel;

    private EnvironmentYAMLReader(EnvironmentType environmentType) {

        EnvironmentType environment;

        // STAGING as Default
        environment = (environmentType != null) ? environmentType : EnvironmentType.STAGING;

        LOGGER.info(String.format(LogConstants.ENVIRONMENT_INITIALIZATION_MESSAGE, environment.toString()));
        try {
            environmentGenericModel = ConvertUtil.convertYmlFileToObject(environment.getUrl(), GenericModel.class);
        } catch (AutomationUtilException e) {
            throw new AutomationTestRunException(LogConstants.FAILED_TO_INITIAL_TEST_ENVIRONMENT, e);
        }
    }

    public static EnvironmentYAMLReader getInstance(EnvironmentType environmentType) {
        if (instance == null) {
            instance = new EnvironmentYAMLReader(environmentType);
        }
        return instance;
    }

    public static EnvironmentYAMLReader getInstance() {
        if (instance == null) {
            instance = new EnvironmentYAMLReader(null);
        }
        return instance;
    }

    public String getEnvironmentData(String key) {
        return environmentGenericModel.getProperties().get(key).toString();
    }

    private EnvironmentType convertEnvironmentStringToEnvironmentType(String s) {
        EnvironmentType environmentType = EnvironmentType.STAGING;
        try {
            environmentType = EnvironmentType.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOGGER.error(String.format(LogConstants.TEST_ENVIRONMENT_DOES_NOT_EXISTS, s) + e);
        }
        return environmentType;
    }
}