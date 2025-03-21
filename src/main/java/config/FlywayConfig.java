package config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.flywaydb.core.Flyway;

public class FlywayConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost:3306/food_delivery_db", "root", "password123#")
                .baselineOnMigrate(true)
                .baselineVersion("1")
                .load();

        flyway.migrate();

    }
}
