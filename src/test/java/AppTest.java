import db.SessionFactories;
import db.TransactionHelpper;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import orms.DateEvent;
import orms.PhoneType;
import orms.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

class A implements Serializable {
    String name;
}

public class AppTest {

    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void setup(){
        sessionFactory = new SessionFactories()
                .addSF("mysql", "hibernate.cfg.mysql.xml"
                        , DateEvent.class
                        , User.class);
    }

    @Test
    public void testFunc()
    {

        TransactionHelpper.transaction(sessionFactory,session -> {

            Date time = Calendar.getInstance().getTime();
            DateEvent dateEvent = new DateEvent();
            dateEvent.setTimestamp(time);
            session.save(dateEvent);
        });

    }

    @Test
    public void testFunc2()
    {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate.cfg.mysql.xml");
        EntityManager entityManager = sessionFactory.createEntityManager();

        entityManager = entityManagerFactory.createEntityManager();


        Date time = Calendar.getInstance().getTime();
        DateEvent dateEvent = new DateEvent();
        dateEvent.setTimestamp(time);

        entityManager.persist(time);
        entityManager.flush();


    }

    @Test
    public void testFuncEM()
    {

        TransactionHelpper.transaction(sessionFactory,session -> {
            EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();
        Date time = Calendar.getInstance().getTime();
        DateEvent dateEvent = new DateEvent();
        dateEvent.setTimestamp(time);

        entityManager.persist(time);
        entityManager.flush();

        });
//        EntityManager entityManager = sessionFactory.createEntityManager();





    }

}