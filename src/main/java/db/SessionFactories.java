package db;

import io.reactivex.Observable;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.Map;

public class SessionFactories {

    private Map<String, SessionFactory> sfs = new HashMap<>();
    public SessionFactory getSF(String sf_name) {
        return sfs.get(sf_name);
    }
    public SessionFactory addSF(String sf_name, String configFile, Class... classes){
        try {
            Configuration cfg = new Configuration().configure(configFile);
            /*cfg.addAnnotatedClass(Member.class);
            StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
            sb.applySettings(cfg.getProperties());
            StandardServiceRegistry standardServiceRegistry = sb.build();
            sessionFactory = cfg.buildSessionFactory(standardServiceRegistry);*/
//            cfg.addAnnotatedClass(Agoda_url_2018.class);
//            cfg.addAnnotatedClass(Agoda_hotel_address.class);
            Observable.fromArray(classes).subscribe(aClass -> {
                cfg.addAnnotatedClass(aClass);

            });
            return cfg.buildSessionFactory();
        } catch (Throwable th) {
            System.err.println("Enitial SessionFactory creation failed" + th);
            throw new ExceptionInInitializerError(th);
        }
    }

}
