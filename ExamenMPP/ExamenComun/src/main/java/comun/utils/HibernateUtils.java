package comun.utils;

import comun.domain.Box;
import comun.domain.Cuvant;
import comun.domain.GameTable;
import comun.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory==null)||(sessionFactory.isClosed()))
            sessionFactory=createNewSessionFactory();
        return sessionFactory;
    }

    private static SessionFactory createNewSessionFactory(){
        sessionFactory = new Configuration()
//              .addAnnotatedClass(CLASA.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(GameTable.class)
                .addAnnotatedClass(Box.class)
                .addAnnotatedClass(Cuvant.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    public static void closeSessionFactory(){
        if (sessionFactory!=null)
            sessionFactory.close();
    }
}
