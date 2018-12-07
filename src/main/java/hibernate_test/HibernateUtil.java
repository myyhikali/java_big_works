package hibernate_test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

public class HibernateUtil {

    private static SessionFactory sessionFactory
            = new Configuration().configure().buildSessionFactory();

    public static Session getSession(){
        Session session = sessionFactory.openSession();
        return session;
    }
    public static void main(String[] args) throws IOException {
        Session session =HibernateUtil.getSession();

    }
}
