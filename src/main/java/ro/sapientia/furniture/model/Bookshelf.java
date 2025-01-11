package ro.sapientia.furniture.model;

import ro.sapientia.furniture.util.Category;

import java.io.Serializable;
import java.util.ArrayList;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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

    @Override
    public String toString() {
        return "Bookshelf [id=" + id + ", category=" + category + ", capacity=" + capacity + ", books=" + books + "]";
    }
}
