package base;

import org.hibernate.SessionFactory;

public interface IApp extends Runnable {
    SessionFactory initDB();
}
