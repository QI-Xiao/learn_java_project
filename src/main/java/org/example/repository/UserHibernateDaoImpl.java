package org.example.repository;

import org.example.model.User;
import org.example.repository.exception.UserNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserHibernateDaoImpl implements IUserDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> getUsers() {
        logger.info("Start getUsers from postgres via hibernate");

        List<User> userList = new ArrayList<>();

        Session session = sessionFactory.openSession();
        try {
            String hql = "from User";
            Query<User> query = session.createQuery(hql);

            userList = query.list();

            session.close();
        } catch (HibernateException e) {
            logger.error("Open session error", e);
            session.close();
        }

        logger.info("Get user {}", userList);
        return userList;
    }

    @Override
    public User save(User user) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                logger.error("Rollback create");
                transaction.rollback();
            }
            logger.error("create error", e);
            session.close();
            return null;
        }
        return user;
    }

    @Override
    public User getUserByCredentials(String email, String password) throws UserNotFoundException {
        String hql = "FROM User as u where (lower(u.email) = :email or lower(u.name) = :email) and u.password = :password";

        try {
            Session session = sessionFactory.openSession();
            Query<User> query = session.createQuery(hql);

            query.setParameter("email", email.toLowerCase().trim());
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch (Exception e) {
            logger.error("error {}", e.getMessage());
            throw new UserNotFoundException("can't find user record with email=" + email + ", password=" + password);
        }
    }

    @Override
    public User getById(long id) {
        Session session = sessionFactory.openSession();
        String hql = "FROM User u where id= :Id";

        try {
            Query<User> query = session.createQuery(hql);
            query.setParameter("Id", id);
            User result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Session exception", e);
            session.close();
            return null;
        }
    }
}
