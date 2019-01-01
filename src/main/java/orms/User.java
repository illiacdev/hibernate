package orms;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue
    int id;

    @Enumerated(EnumType.ORDINAL)
    PhoneType phoneType;

    @CreationTimestamp
    private Date create_date;

    @UpdateTimestamp
    private Date update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phoneType=" + phoneType +
                '}';
    }
}
