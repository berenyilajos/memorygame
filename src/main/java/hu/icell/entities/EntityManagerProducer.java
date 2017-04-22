package hu.icell.entities;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author lajos
 * 
 */
@Model
public class EntityManagerProducer {

    @PersistenceContext(unitName = "memorygame")
    private static EntityManager entityManager;

    @Produces
    public static EntityManager createEntityManager() {
        return entityManager;
    }
}

