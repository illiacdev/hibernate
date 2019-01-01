import db.SessionFactories;
import io.reactivex.Observable;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;
import org.hibernate.testing.transaction.TransactionUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import orms.PhoneType;
import orms.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "Book")
class Book {

    @Id
    private Long id;

    private String title;

    private String author;

    @ManyToMany
    @JoinTable(
            name = "Book_Reader",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "reader_id")
    )
    @WhereJoinTable( clause = "created_on > DATEADD( 'DAY', -7, CURRENT_TIMESTAMP() )")
    private List<Reader> currentWeekReaders = new ArrayList<>( );

    //Getters and setters omitted for brevity

}

@Entity(name = "Reader")
class Reader {

    @Id
    private Long id;

    private String name;

    //Getters and setters omitted for brevity

}

 enum AccountType {
    DEBIT,
    CREDIT
}

@Entity(name = "Client")
  class Client {
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Id
    private Long id;

    private String name;

    @Where( clause = "account_type = 'DEBIT'")
    @OneToMany(mappedBy = "client")
    private List<Account> debitAccounts = new ArrayList<>( );

    @Where( clause = "account_type = 'CREDIT'")
    @OneToMany(mappedBy = "client")
    private List<Account> creditAccounts = new ArrayList<>( );

    //Getters and setters omitted for brevity

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getDebitAccounts() {
        return debitAccounts;
    }

    public void setDebitAccounts(List<Account> debitAccounts) {
        this.debitAccounts = debitAccounts;
    }

    public List<Account> getCreditAccounts() {
        return creditAccounts;
    }

    public void setCreditAccounts(List<Account> creditAccounts) {
        this.creditAccounts = creditAccounts;
    }
}

@Entity(name = "Account")
@Where( clause = "active = true" )
  class Account {

    @Id
    private Long id;

    @ManyToOne
    private Client client;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    private Double amount;

    private Double rate;

    private boolean active;

    //Getters and setters omitted for brevity

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", client=" + client +
                ", type=" + type +
                ", amount=" + amount +
                ", rate=" + rate +
                ", active=" + active +
                '}';
    }
}


public class EntityManagerTest {

    @BeforeClass
    public static void setup(){

    }
    @Test
    public void testFunc()
    {
        SessionFactory sessionFactory = new SessionFactories()
                .addSF("mysql", "hibernate.cfg.mysql.xml"
                        , User.class);

        EntityManager em = sessionFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        User user = new User();
        user.setPhoneType(PhoneType.T_스마트폰);

        em.persist(user);
        em.flush();

//        User user1 = em.find(User.class, 1);
//        System.out.println(user1);

        transaction.commit();


    }

    @Test
    public void testFunc2()
    {
        SessionFactory sessionFactory = new SessionFactories()
                .addSF("mysql", "hibernate.cfg.mysql.xml"
                        , User.class);

        EntityManager em = sessionFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        List<User> list = em.createQuery("select m from User m", User.class).getResultList();

        Observable.fromIterable(list).subscribe(user -> {
            System.out.println(user);
        });

        transaction.commit();
    }

    @Test
    public void testFuncUpdate()
    {
        SessionFactory sessionFactory = new SessionFactories()
                .addSF("mysql", "hibernate.cfg.mysql.xml"
                        , Client.class
                        , Account.class
                        , User.class
                );


        TransactionUtil.doInJPA(() -> sessionFactory, entityManager -> {

            Client client = new Client();
            client.setId( 1L );
            client.setName( "John Doe" );
            entityManager.persist( client );

            Account account1 = new Account( );
            account1.setId( 1L );
            account1.setType( AccountType.CREDIT );
            account1.setAmount( 5000d );
            account1.setRate( 1.25 / 100 );
            account1.setActive( true );
            account1.setClient( client );
            client.getCreditAccounts().add( account1 );
            entityManager.persist( account1 );

            Account account2 = new Account( );
            account2.setId( 2L );
            account2.setType( AccountType.DEBIT );
            account2.setAmount( 0d );
            account2.setRate( 1.05 / 100 );
            account2.setActive( false );
            account2.setClient( client );
            client.getDebitAccounts().add( account2 );
            entityManager.persist( account2 );

            Account account3 = new Account( );
            account3.setType( AccountType.DEBIT );
            account3.setId( 3L );
            account3.setAmount( 250d );
            account3.setRate( 1.05 / 100 );
            account3.setActive( true );
            account3.setClient( client );
            client.getDebitAccounts().add( account3 );
            entityManager.persist( account3 );

        });

    }

    @Test
    public void testFunc조회()
    {

        TransactionUtil.doInJPA(() -> new SessionFactories()
                .addSF("mysql", "hibernate.cfg.mysql.xml"
                        , Client.class
                        , Account.class
                        , User.class
                ), em -> {

            em.find(Client.class,1L).getDebitAccounts() .forEach(account -> {
                System.out.println(account);
            });

        });
    }

    @Test
    public void testFunc조회2()
    {
        TransactionUtil.doInJPA(() -> new SessionFactories()
                .addSF("mysql", "hibernate.cfg.mysql.xml"
                        , Client.class
                        , Account.class
                        , User.class
                ), em -> {

            em.createQuery("from Account",Account.class).getResultList().forEach(account -> {
                System.out.println(account);
            });

        });

    }


    @Test
    public void testFunc_WhereJoinTable()
    {
        TransactionUtil.doInJPA(() -> new SessionFactories()
                .addSF("mysql", "hibernate.cfg.mysql.xml"
                        , Book.class
                        , Reader.class
                ), em -> {


        });

    }


}
