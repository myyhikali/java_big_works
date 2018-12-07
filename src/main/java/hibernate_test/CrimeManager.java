package hibernate_test;

import model.BeanCrime;
import org.hibernate.Query;
import org.hibernate.Session;
//import org.springframework.context.annotation.Bean;


import java.io.IOException;
import java.util.*;

public class CrimeManager {
    public BeanCrime CreateCrime(BeanCrime crime) throws BaseException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        if(crime==null)
            throw new BaseException("null");
        session.save(crime);

        session.getTransaction().commit();
        session.close();
        return crime;
    }

    public void UpdateFirstPrisoner(int prisonerid,int crimeid)
    {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        BeanCrime crime = (BeanCrime) session.get(BeanCrime.class, crimeid);

        crime.setFirstprisonerid(prisonerid);

        session.getTransaction().commit();
        session.close();
    }

    public static BeanCrime MaxCrime() throws BaseException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        String hql="Select Max(a.crimeid) From BeanCrime as a";
        int crimeid=(int)session.createQuery(hql).uniqueResult();

        BeanCrime crime= (BeanCrime) session.get(BeanCrime.class,crimeid);

        session.getTransaction().commit();
        session.close();
       // System.out.println("------"+crimeid+crime.getCrimeid());
        return crime;
    }

    public static List<BeanCrime> loadAllCrimes() throws BaseException {
        List<BeanCrime> list=new ArrayList<>();
        try{
            Session session=HibernateUtil.getSession();
            session.beginTransaction();
            String hql="From BeanCrime";

            Query qry = session.createQuery(hql);

            list=qry.list();
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public static Map<Integer,Integer> loadMouthCrime() throws BaseException {
        Map<Integer,Integer> map=new HashMap<>();
        map.put(1, 0);
        map.put(2, 0);
        map.put(3, 0);
        map.put(4, 0);
        map.put(5, 0);
        map.put(6, 0);
        map.put(7, 0);
        map.put(8, 0);
        map.put(9, 0);
        map.put(10, 0);
        map.put(11, 0);
        map.put(12, 0);
        List<BeanCrime> list=loadAllCrimes();
        Calendar calendar=Calendar.getInstance();
        int value;
        int mouth;
       // System.out.println("---------");
        for (BeanCrime crime:list){
         //   System.out.println(crime.getDate());
            calendar.setTime(crime.getDate());

            mouth=calendar.get(Calendar.MONTH);
           // System.out.println(calendar.get(Calendar.YEAR));

            mouth+=1;
            //System.out.println(mouth);
            value=map.get(mouth);
            map.put(mouth, ++value);
        }
        return map;
    }
    public static void main(String[] args) throws IOException {

        try{
            Map<Integer,Integer>  map=CrimeManager.loadMouthCrime();
            System.out.println(map);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
