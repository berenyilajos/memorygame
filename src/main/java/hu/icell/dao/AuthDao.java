package hu.icell.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import hu.icell.encrypt.Encrypter;
import hu.icell.entities.User;

public class AuthDao {
    @PersistenceContext(unitName="memorygame")
    private EntityManager em;
    
    public User getUserByUsernameAndPassword(String username, String password) {
        Query q = em.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:password");
        q.setParameter("username", username);
        q.setParameter("password", Encrypter.getMD5(password));
        User user = null;
        try {
            user = (User) q.getSingleResult();
        } catch (Exception e) {
        }
        
        return user;
    }
    
    public User getUserByUsername(String username) {
        Query q = em.createQuery("SELECT u FROM User u WHERE u.username=:username");
        q.setParameter("username", username);
        User user = null;
        try {
            user = (User) q.getSingleResult();
        } catch (Exception e) {
        }
        
        return user;
    }
    
    @Transactional
    public void saveUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(Encrypter.getMD5(password));
        user.setEmail(username + "@example.com");
        em.persist(user);
    }

}
