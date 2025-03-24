package com.app.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.flywaydb.core.Flyway;

public class FlywayConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource("jdbc:mysql://localhost:3306/food_delivery_db",
                            "root", "password123#")
                    .locations("classpath:db.migration")
                    .baselineOnMigrate(true)
                    .baselineVersion("2")
                    .load();
            flyway.baseline();
            flyway.migrate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
