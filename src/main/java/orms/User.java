package orms;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue
    int id;

    @Enumerated(EnumType.STRING)
    PhoneType phoneType;

    @Transient
    int test;

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
