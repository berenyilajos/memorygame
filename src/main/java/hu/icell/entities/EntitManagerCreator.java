package hu.icell.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class EntitManagerCreator {
	@PersistenceContext
	private static EntityManager em;
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("memorygame");
	
	public static EntityManager getEntityManager() {
		if (em == null) {
			em = emf.createEntityManager();
		}
		
		return em;
	}
	
}
