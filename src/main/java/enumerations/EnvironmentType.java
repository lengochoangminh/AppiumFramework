package enumerations;

public enum EnvironmentType {

    STAGING("src/main/resources/environments/staging.yml"),
    DEMO("src/main/resources/environments/demo.yml"),
    PRODUCTION("src/main/resources/environments/production.yml");

    private final String url;

    EnvironmentType(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}