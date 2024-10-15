package fun.archyonix.rpg;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

// @ Postgres Only
public class DatabaseTools {
    private final String url;
    private final String username;
    private final String password;

    private final SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public DatabaseTools(String host, String username, String password) {
        this.url = String.format("jdbc:postgresql://%s/core", host);
        this.username = username;
        this.password = password;

        initLiquibase();
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        sessionFactory = new Configuration()
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", this.url)
                .setProperty("hibernate.connection.username", this.username)
                .setProperty("hibernate.connection.password", this.password)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.show_sql", "false")
                .setProperty("hibernate.current_session_context_class", "thread")
                .buildSessionFactory();
    }

    private void initLiquibase() {
        Liquibase liquibase;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            liquibase = new Liquibase("db.changelog/init.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.setChangeLogParameter("liquibase-logLevel", "OFF");
            liquibase.setChangeLogParameter("liquibase.sql.logLevel", "OFF");
            liquibase.setChangeLogParameter("liquibase.searchPath", "/");
            liquibase.update("");
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
