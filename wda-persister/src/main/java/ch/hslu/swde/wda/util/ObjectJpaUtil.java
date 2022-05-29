package ch.hslu.swde.wda.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public final class ObjectJpaUtil {

    private static Logger LOG = LogManager.getLogger(ObjectJpaUtil.class);

    private static EntityManagerFactory entityManagerFactory = null;

    private ObjectJpaUtil() {
    }

    static {
        try {
            // create EntityManagerFactory
            entityManagerFactory = Persistence.createEntityManagerFactory("PersisterImpl");
        } catch (Exception e) {
            LOG.error("ERROR: ", e);
            throw new RuntimeException(e);
        }
    }

    public static EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
