package io.micronaut.configuration.clickhouse;

import com.clickhouse.client.ClickHouseCredentials;
import io.micronaut.context.exceptions.ConfigurationException;

/**
 * @see com.clickhouse.client.ClickHouseCredentials
 * @author Anton Kurako (GoodforGod)
 * @since 13.04.2023
 */
public class ClickHouseClientCredentials {

    private String accessToken;
    private String userName;
    private String password;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        if(userName != null || password != null) {
            throw new ConfigurationException("Username or password is already specified, please use access token or username and password instead.");
        }
        this.accessToken = accessToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if(accessToken != null) {
            throw new ConfigurationException("Access token is already specified, please use access token or username and password instead.");
        }
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(accessToken != null) {
            throw new ConfigurationException("Access token is already specified, please use access token or username and password instead.");
        }
        this.password = password;
    }

    public ClickHouseCredentials getCredentials() {
        return (accessToken != null)
                ? ClickHouseCredentials.fromAccessToken(accessToken)
                : ClickHouseCredentials.fromUserAndPassword(userName, password);
    }

    @Override
    public String toString() {
        if (accessToken == null) {
            return "userName=" + userName + ", password=" + password + ']';
        } else {
            return "[accessToken=" + accessToken + ']';
        }
    }
}
