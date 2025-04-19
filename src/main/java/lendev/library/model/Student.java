package lendev.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public String registration;
    public Integer penaltyCount = 0;
}
