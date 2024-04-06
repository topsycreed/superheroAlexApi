package configs;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${env}.properties",
        "classpath:default.properties"
})
public interface TestPropertiesConfig extends org.aeonbits.owner.Config {
    @Key("baseUrl")
    String getBaseUrl();

    @Key("login")
    String getLogin();

    @Key("password")
    String getPassword();
}
