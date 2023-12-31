package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import constants.LogConstants;
import exceptions.AutomationUtilException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ConvertUtil {

    private ConvertUtil() {
    }

    private static final Logger LOGGER = LogManager.getLogger("myTestLog4j");

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T convertMapToObject(Map<Object, Object> map, final Class<T> clazz) {
        return mapper.convertValue(map, clazz);
    }

    public static String convertMapToJsonString(Map<Object, Object> map) throws JsonProcessingException {
        return mapper.writeValueAsString(map);
    }

    public static <T> T convertJsonFileToObject(final String filePath, final Class<T> clazz) {
        LOGGER.info(LogConstants.CONVERT_FILE_PATH + filePath + "\n");
        try {
            return mapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            throw new AutomationUtilException(LogConstants.CONVERT_JSON_FILE_TO_OBJECT, e);
        }
    }


    public static <T> T convertYmlFileToObject(final String filePath, final Class<T> clazz) {

        LOGGER.info(LogConstants.CONVERT_FILE_PATH + filePath + "\n");
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            return mapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            throw new AutomationUtilException(LogConstants.CONVERT_YML_FILE_TO_OBJECT, e);
        }
    }

    public static Map<Object, Object> convertJsonFileToMap(final String fileName) throws IOException {
        return mapper.readValue(new File(fileName), new TypeReference<Map<Object, Object>>() {
        });
    }

    public static <T> T convertGenericObjectToObject(Object genericObject, final Class<T> clazz) {
        try {
            final byte[] bytes = mapper.writeValueAsBytes(genericObject);
            return mapper.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new AutomationUtilException(LogConstants.CONVERT_GENERIC_OBJECT_TO_OBJECT, e);
        }
    }
}