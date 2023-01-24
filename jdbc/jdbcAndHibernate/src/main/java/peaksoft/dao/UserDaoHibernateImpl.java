package peaksoft.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao{

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSession().openSession();
        session.beginTransaction();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.getSession().openSession();
            session.beginTransaction();
            session.createSQLQuery("drop table users").executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("Successfully drop table....");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try {
            Session session = Util.getSession().openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
            System.out.println("Successfully saved !");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long idUser) {
        try (Session session = Util.getSession().openSession()) {
            session.beginTransaction();
            User users = session.get(User.class, idUser);
            session.delete(users);
            session.getTransaction().commit();
            session.close();
            System.out.println("Successfully removed .....");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSession().openSession()) {
            session.beginTransaction();
            List<User> users = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            session.close();
            return users;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession().openSession();
        session.beginTransaction();
        session.createQuery("delete User ").executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Table successfully cleaned..... ");
    }
}
