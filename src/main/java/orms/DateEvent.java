package orms;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "DateEvent")
public class DateEvent {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "`timestamp`")
//    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    //Getters and setters are omitted for brevity


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}