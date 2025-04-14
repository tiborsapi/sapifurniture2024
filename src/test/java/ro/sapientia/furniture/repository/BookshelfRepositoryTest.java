package ro.sapientia.furniture.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ro.sapientia.furniture.model.Book;
import ro.sapientia.furniture.model.Bookshelf;
import ro.sapientia.furniture.util.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class BookshelfRepositoryTest {
    @Autowired
    BookshelfRepository repository;

    @Test
    public void testFindAllWhenNoBookshelvesExist() {
        var result = repository.findAll();
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testFindBookshelfByCategory() {
        Bookshelf bookshelf1 = new Bookshelf();
        bookshelf1.setCapacity(20);
        bookshelf1.setCategory(Category.SCIENCE);
        repository.save(bookshelf1);
        var bookshelfTypeScience = repository.findBookshelfByCategory(Category.SCIENCE);

        Assertions.assertEquals(1, bookshelfTypeScience.size());

        Bookshelf bookshelf2 = new Bookshelf();
        bookshelf2.setCapacity(10);
        bookshelf2.setCategory(Category.FICTION);
        repository.save(bookshelf2);
        var bookshelfTypeFiction = repository.findBookshelfByCategory(Category.FICTION);

        Assertions.assertEquals(1, bookshelfTypeFiction.size());
    }

    @Test
    public void testFindBookshelfByCapacity() {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCapacity(30);
        bookshelf.setCategory(Category.CHILDREN);
        repository.save(bookshelf);
        var result = repository.findAll();

        Assertions.assertEquals(30, result.get(0).getCapacity());

        var result2 = repository.findBookshelfByCapacity(30);
        Assertions.assertNotEquals(Category.FICTION, result2.get(0).getCategory());
    }

    @Test
    public void testFindAllWithMultipleBookshelves() {
        Bookshelf bookshelf1 = new Bookshelf();
        bookshelf1.setCapacity(10);
        bookshelf1.setCategory(Category.SCIENCE);
        repository.save(bookshelf1);

        Bookshelf bookshelf2 = new Bookshelf();
        bookshelf2.setCapacity(10);
        bookshelf2.setCategory(Category.FICTION);
        repository.save(bookshelf2);
        var result = repository.findAll();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testDeleteBookshelf() {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCapacity(10);
        bookshelf.setCategory(Category.SCIENCE);
        var savedBookshelf = repository.save(bookshelf);
        var result1 = repository.findAll();

        Assertions.assertEquals(1, result1.size());

        repository.delete(savedBookshelf);
        var result2 = repository.findAll();
        Assertions.assertEquals(0, result2.size());
    }

    @Test
    public void testAddBookToBookshelf() {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCapacity(10);
        bookshelf.setCategory(Category.FICTION);
        var savedBookshelf = repository.save(bookshelf);
        var result1 = repository.findAll();

        Assertions.assertEquals(0, result1.get(0).getBooks().size());

        Book book = new Book();
        book.setAuthor("Orwell");
        book.setTitle("1984");
        book.setCategory(Category.FICTION);

        savedBookshelf.getBooks().add(book);
        repository.save(savedBookshelf);

        var result2 = repository.findAll();
        Assertions.assertEquals(1, result2.get(0).getBooks().size());
    }
}
