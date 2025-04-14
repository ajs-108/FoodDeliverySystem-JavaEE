package com.app.common.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryUtil {
    private static final String PERSISTENCE_UNIT_NAME = "FoodDeliveryPersistentUnit";
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEmfInstance() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return emf;
    }

    public static void closeEmfInstance() {
        if (emf != null) {
            emf.close();
        }
    }
}
