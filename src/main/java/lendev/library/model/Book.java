package lendev.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue
    public Long id;

    public String title;
    public String author;
    public String isbn;
    public String genre;
    public Integer quantity;
}
