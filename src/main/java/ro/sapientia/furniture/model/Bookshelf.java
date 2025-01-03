package ro.sapientia.furniture.model;

import ro.sapientia.furniture.util.Book;
import ro.sapientia.furniture.util.Category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.*;

@Entity(name = "bookshelf")
public class Bookshelf implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_bookshelf")
    @SequenceGenerator(name = "pk_bookshelf", sequenceName = "pk_bookshelf")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "bookshelf_id")
    private List<Book> books = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Book> getBooks() {
        return books;
    }

    public boolean addBook(String title, String author, Category bookCategory) {
        if (bookCategory == this.category && books.size() < capacity) {
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setCategory(bookCategory);
            books.add(book);
            return true;
        }
        return false;
    }

    public boolean removeBook(String title) {
        return books.removeIf(book -> book.getTitle().equals(title));
    }

    public boolean containsBook(String title) {
        return books.stream().anyMatch(book -> book.getTitle().equals(title));
    }

    public void sortBooks() {
        books.sort(Comparator.comparing(Book::getTitle));
    }

    public int countBooks() {
        return books.size();
    }

    @Override
    public String toString() {
        return "Bookshelf [id=" + id + ", category=" + category + ", capacity=" + capacity + ", books=" + books + "]";
    }
}
