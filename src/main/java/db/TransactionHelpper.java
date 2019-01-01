package db;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TransactionHelpper {
    static public void transaction(SessionFactory factory, Consumer<Session> consumer) {
        Session session =  null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            consumer.accept(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
        }
    }

    static public <T> void transaction(SessionFactory factory, Function<Session,T> function, Consumer<T> consumer) {
        Session session =  null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            T apply = function.apply(session);
            session.getTransaction().commit();

            consumer.accept(apply);


        } catch (Exception e) {
            e.printStackTrace();
            session.close();
        }
    }

}
