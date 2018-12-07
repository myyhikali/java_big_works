package hibernate_test;

import model.BeanPrisoner;

import org.hibernate.Query;
import org.hibernate.Session;
//import org.hibernate.query.Query;
//import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.*;

public class PrisonerManager {
    public void CreatePrisoner(BeanPrisoner prisoner) throws BaseException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        if(prisoner==null)
            throw new BaseException("null");
        session.save(prisoner);
        session.getTransaction().commit();
        session.close();

        System.out.println("-------"+prisoner.getCrimeid());
    }
    public List<BeanPrisoner> loadAllPrisoners() throws BaseException {
        List<BeanPrisoner> list=new ArrayList<>();
        try{
            Session session=HibernateUtil.getSession();
            session.beginTransaction();
            String hql="From BeanPrisoner";

            org.hibernate.Query qry = session.createQuery(hql);

            list=qry.list();
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public static Map<String,Integer> loadPrisonersAge() throws BaseException {
        Map<String,Integer> map=new HashMap<>() ;
        List<BeanPrisoner> list=new ArrayList<>();

        map.put("20岁以下", 0);
        map.put("20~30岁", 0);
        map.put("30~40岁", 0);
        map.put("40~50岁", 0);
        map.put("50~60岁", 0);
        map.put("60岁以上", 0);

        try{
            Session session=HibernateUtil.getSession();
            session.beginTransaction();
            String hql="From BeanPrisoner";

            org.hibernate.Query qry = session.createQuery(hql);

            list=qry.list();
            session.getTransaction().commit();
            session.close();
            Date now=new Date(System.currentTimeMillis());
            int value;
            Calendar calendar_now=Calendar.getInstance();
            calendar_now.setTime(new Date(System.currentTimeMillis()));
            //System.out.println(calendar_now.get(Calendar.YEAR));

            Calendar calendar_past=Calendar.getInstance();
            for(BeanPrisoner prisoner:list){
                calendar_past.setTime(prisoner.getBirth());
                int age=calendar_now.get(Calendar.YEAR)-calendar_past.get(Calendar.YEAR);
                if(age<20){

                   value= map.get("20岁以下");
                   map.put("20岁以下",++value );
                }
                else if(20<=age&&age<30){
                    value= map.get("20~30岁");
                    map.put("20~30岁",++value );
                }
                else if(30<=age&&age<40){
                    value= map.get("30~40岁");
                    map.put("30~40岁",++value );
                }
                else if(40<=age&&age<50){
                    value= map.get("40~50岁");
                    map.put("40~50岁",++value );
                }
                else if(50<=age&&age<60){
                    value= map.get("50~60岁");
                    map.put("50~60岁",++value );
                }
                else {
                    value= map.get("60岁以上");
                    map.put("60岁以上",++value );

                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
    public BeanPrisoner hasPrisoner(String name) throws  BaseException{
        Session session = HibernateUtil.getSession();

        Query qry = (Query) session.createQuery("from BeanPrisoner where name = :Name");

        qry.setString("Name", name);

        if(qry.list()!=null&&qry.list().size()>0)
        {
            BeanPrisoner prisoner = (BeanPrisoner) qry.list().get(0);
            session.close();
            return  prisoner;
        }
        session.close();
        return null;
    }

    public static BeanPrisoner getPrisoner(int id) throws BaseException{
        Session session = HibernateUtil.getSession();

        return (BeanPrisoner) session.get(BeanPrisoner.class,id);

    }
    public static void main(String[] args) throws IOException {
        PrisonerManager prisonerManager=new PrisonerManager();
//        try{
//            Map<String,Integer>  map=prisonerManager.loadPrisonersAge();
//            System.out.println(map);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        try {

            BeanPrisoner p =prisonerManager.hasPrisoner("林永良");

            System.out.println(p.getPrisonerid());
        } catch (BaseException e) {
            e.printStackTrace();
        }


    }

}
