package data.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory ENTITY_MANAGER_FACTORY;

    static {
        try {
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate-unit");
        } catch (Exception e) {
            System.out.println("Exception while trying to load Entity Manager Factory.. -> " + e.getMessage() + "\n" + e.getLocalizedMessage() + "\n");
            e.printStackTrace();
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }

}

