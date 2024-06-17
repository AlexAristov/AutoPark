package ru.aristov.park.utlils;

import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBUtils {
    private static final HikariDataSource dataSource;
    public static HikariDataSource getDataSource() {
        return dataSource;
    };

    static {
        Properties properties = new Properties();

        String url;
        String username;
        String password;
        String driverClassName;

        try (InputStream streamProperties = DBUtils.class.getClassLoader().getResourceAsStream("db.properties")){
            properties.load(streamProperties);

            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
            driverClassName = properties.getProperty("db.driverClassName");
//            Class.forName(driverClassName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);

        dataSource.setMinimumIdle(5);
        dataSource.setMaximumPoolSize(50);
    }
}
