package hibernate_test;

import model.BeanCase;
import model.BeanCrime;
import org.hibernate.Session;

public class CaseManager {
    public BeanCase CreateCase(BeanCase Case) throws BaseException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        if(Case==null)
            throw new BaseException("null");
        session.save(Case);

        session.getTransaction().commit();
        session.close();
        return Case;
    }
}
