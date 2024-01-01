package constants;

public class LogConstants {

    private LogConstants() {
    }

    //Environment log
    public static final String FILE_NOT_FOUND = "File NOT FOUND >>>> ";
    public static final String CANNOT_GET_PROPERTIES_FROM_FILE = "Cannot get properties from file, please check your file name >>>> ";
    public static final String TARGET_PROPERTIES_NULL = "Target properties is null >>> ";

    //Convert log
    public static final String CONVERT_FILE_PATH = "File path >>>> ";
    public static final String CONVERT_JSON_FILE_TO_OBJECT = "Failed to convert json file to object >>>> ";
    public static final String CONVERT_YML_FILE_TO_OBJECT = "Failed to convert yml file to object >>>> ";
    public static final String CONVERT_JSON_FILE_TO_MAP = "Failed to convert json file to map >>>> ";
    public static final String CONVERT_GENERIC_OBJECT_TO_OBJECT = "Failed to convert generic object to object >>>> ";

    //Environment configuration log
    public static final String ENVIRONMENT_INITIALIZATION_MESSAGE = "******** INITIALIZING TEST ENVIRONMENT [%s] ********";
    public static final String FAILED_TO_INITIAL_TEST_ENVIRONMENT = "Failed to initializing test dataHandler, please check your yaml config file!!!";
    public static final String TEST_ENVIRONMENT_DOES_NOT_EXISTS = "The test dataHandler [%s] doesn't exists. Please input the correct one! Error >>>> ";

    // Test data file
    public static final String STAGE_TEST_DATA_NAME = "src/test/resources/testData/stage.json";
    public static final String DEMO_TEST_DATA_NAME = "src/test/resources/testData/demo.json";
    public static final String PROD_TEST_DATA_NAME = "src/test/resources/testData/prod.json";
}
