import base.IApp;
import db.SessionFactories;
import org.hibernate.SessionFactory;
import orms.User;

public class App implements IApp {
    public static void main(String[] args) {
        new App().run();

    }
    @Override
    public SessionFactory initDB() {
        SessionFactory sessionFactory = new SessionFactories()
                .addSF("mysql", "META-INF/"
                        , User.class);
        return sessionFactory;
    }

    @Override
    public void run() {
        initDB();
    }



}
