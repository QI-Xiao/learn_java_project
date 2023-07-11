package org.example.repository;

import com.sun.xml.fastinfoset.util.StringArray;
import org.example.model.Department;
import org.example.util.HibernateUtil;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DepartmentHibernateDaoImpl implements IDepartmentDao {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentHibernateDaoImpl.class);

    @Override
    public void save(Department department) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(department);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                logger.error("Rollback create");
                transaction.rollback();
            }
            logger.error("create error", e);
        }
    }

    @Override
    public List<Department> getDepartments() {
        logger.info("Start getDepartments from postgres via hibernate");

        List<Department> departments = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();
        try {
            String hql = "from Department";
            Query<Department> query = session.createQuery(hql);

            departments = query.list();

            session.close();
        } catch (HibernateException e) {
            logger.error("open session error", e);
            throw e;
//            session.close();
        }

        logger.info("set department {}", departments);
        return departments;
    }

    @Override
    public Department getById(long id) {
        return IDepartmentDao.super.getById(id);
    }

    @Override
    public boolean delete(Department department) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Transaction tx = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.delete(department);
            tx.commit();
            session.close();
        } catch (HibernateException e) {
            if (tx != null) {
                logger.error("Rollback delete");
                tx.rollback();
            }
            logger.error("delete error", e);
        }
        return true;
    }

    @Override
    public Department getDepartmentEagerBy(Long id) {
        String hql = "FROM Department d LEFT JOIN FETCH d.employees where d.id = :Id"; //LEFT JOIN FETCH: HQL里面的left join
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("Id", id);
            Department result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("failed to retrieve data record", e);
            session.close();
            return null;
        }
    }
}
