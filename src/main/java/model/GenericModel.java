package model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Common data transfer object
 */

public class GenericModel {

    @JsonIgnore
    private Map<String, Object> properties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return this.properties;
    }

    @JsonAnySetter
    public void setProperties(String key, Object value) {
        this.properties.put(key, value);
    }
}