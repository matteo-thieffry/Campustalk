
package bdd;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    public DatabaseConnector() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("res/config.prop")) {
            if (input == null) {
                throw new IOException("Fichier de configuration 'res/config.prop' introuvable dans le classpath.");
            }
            properties.load(input);
            this.dbUrl = properties.getProperty("url");
            this.dbUsername = properties.getProperty("login");
            this.dbPassword = properties.getProperty("password");

            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC introuvable : " + properties.getProperty("driver"), e);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public Connection createConnection() throws SQLException {
        if (dbUrl == null || dbUsername == null || dbPassword == null) {
            throw new SQLException("Les informations de connexion sont incomplètes.");
        }
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }
}
