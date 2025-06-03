
package lendev.library.dto;

//Classe DTO (Data Transfer Object) usada para transferir dados do livro entre camadas da aplicação
public class BookDTO {
	private Long id;
    private String title;
    private String author;
    private String isbn;
    private String genero;
    private Integer quantity;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genero;
    }

    public void setGenre(String genre) {
        this.genero = genre;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

