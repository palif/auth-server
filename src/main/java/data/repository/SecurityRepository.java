package data.repository;

import data.interfaces.ISecurityRepository;
import data.model.User;
import data.util.JPAUtil;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.EntityManager;
import java.util.List;

public class SecurityRepository implements ISecurityRepository<User> {

    protected JPAUtil jpaUtil;

    public SecurityRepository(){
        jpaUtil = new JPAUtil();
    }

    @Override
    public boolean authenticate(User user) {

        if (user == null) return false;
        if (user.getEmail() == null || user.getPassword() == null) return false;
        if (user.getEmail().length() < 5) return false;

        EntityManager em = null;

        try {
            em = jpaUtil.getEntityManagerFactory().createEntityManager();
            List<User> result = em.createQuery("SELECT u FROM User u " +
                    "WHERE u.email = '" + user.getEmail() + "' AND u.password = '" + user.getPassword() + "'" ).getResultList();
            em.close();
            if (result == null) return false;
            if (result.size() == 1) return true;
            return false;

        } catch (Exception e) {
            System.out.println("Exception while trying to authentucate user -> " + e.getMessage());
            if (em != null) {
                em.getTransaction().rollback();
                em.close();
            }
        }

        return false;
    }

    @Override
    public boolean register(User user) {

        if (user == null) return false;
        if (user.getEmail() == null || user.getPassword() == null) return false;
        if (user.getEmail().length() < 5) return false;

        EntityManager em = null;

        try {
            em = jpaUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            System.out.println("Exception while trying to registering user -> " + e.getMessage());
            if (em != null) {
                em.getTransaction().rollback();
                em.close();
            }
        }

        return false;
    }

    @Override
    public boolean isAuthorized(User user) {
        if (user == null) return false;
        if (user.getEmail() == null || user.getPassword() == null) return false;
        if (user.getEmail().length() < 5) return false;

        EntityManager em = null;

        try {
            em = jpaUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            User res = em.find(User.class, user.getId());
            if (res == null) return false;
            return true;

        }catch (Exception e) {
            System.out.println("Exception while trying to authorizing user -> " + e.getMessage());
            if (em != null) {
                em.getTransaction().rollback();
                em.close();
            }
        } finally {
            em.getTransaction().commit();
            em.close();
        }

        return false;
    }

}
