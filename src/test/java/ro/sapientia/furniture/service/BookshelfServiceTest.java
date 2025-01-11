package ro.sapientia.furniture.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.sapientia.furniture.model.Book;
import ro.sapientia.furniture.model.Bookshelf;

import ro.sapientia.furniture.repository.BookshelfRepository;
import ro.sapientia.furniture.util.BookshelfNotFoundException;
import ro.sapientia.furniture.util.Category;
import ro.sapientia.furniture.util.InsufficientCapacityException;
import ro.sapientia.furniture.util.InvalidCategoryException;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

public class BookshelfServiceTest {
    private BookshelfRepository repositoryMock;

    private BookshelfService service;

    @BeforeEach
    public void setUp() {
        repositoryMock = mock(BookshelfRepository.class);
        service = new BookshelfService(repositoryMock);
    }

    @Test
    public void countBooksInBookshelf_ReturnsCorrectCount() throws BookshelfNotFoundException {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.getBooks().add(new Book());
        bookshelf.getBooks().add(new Book());
        bookshelf.getBooks().add(new Book());

        when(repositoryMock.findBookshelfById(1L)).thenReturn(bookshelf);

        int count = service.countBooksInBookshelf(1L);
        Assertions.assertEquals(3, count);
    }

    @Test
    public void sortBooksInBookshelf_ReturnsSortedList() throws BookshelfNotFoundException {
        Bookshelf bookshelf = new Bookshelf();
        Book book1 = new Book();
        Book book2 = new Book();
        Book book3 = new Book();
        book1.setTitle("M_Book");
        book2.setTitle("S_Book");
        book3.setTitle("A_Book");
        bookshelf.getBooks().add(book1);
        bookshelf.getBooks().add(book2);
        bookshelf.getBooks().add(book3);

        when(repositoryMock.findBookshelfById(1L)).thenReturn(bookshelf);
        List<Book> sortedBooks = service.sortBooksInBookshelf(1L);
        Assertions.assertEquals("A_Book", sortedBooks.get(0).getTitle());
    }

    @Test
    public void addBookToBookshelf_ThrowsInvalidCategoryException_WhenCategoryMismatch()
            throws BookshelfNotFoundException, InsufficientCapacityException, InvalidCategoryException {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCategory(Category.CHILDREN);

        when(repositoryMock.findBookshelfById(1L)).thenReturn(bookshelf);

        Assertions.assertThrows(
                InvalidCategoryException.class,
                () -> service.addBookToBookshelf(1L, "1984", "Orwell", Category.FICTION)
        );
    }

    @Test
    public void addBookToBookshelf_ThrowsInsufficientCapacityException_WhenCapacityExceeded()
            throws BookshelfNotFoundException, InsufficientCapacityException, InvalidCategoryException {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCategory(Category.FICTION);
        bookshelf.setCapacity(0);

        when(repositoryMock.findBookshelfById(1L)).thenReturn(bookshelf);

        Assertions.assertThrows(
                InsufficientCapacityException.class,
                () -> service.addBookToBookshelf(1L, "1984", "Orwell", Category.FICTION)
        );
    }

    @Test
    public void findBookshelfById_ThrowsBookshelfNotFoundException_WhenNotFound()
            throws BookshelfNotFoundException {
        when(repositoryMock.findBookshelfById(1L)).thenReturn(null);

        Assertions.assertThrows(
                BookshelfNotFoundException.class,
                () -> service.findBookshelfById(1L)
        );
    }

    @Test
    public void updateBookshelf_UpdatesCapacityAndCategorySuccessfully()
            throws BookshelfNotFoundException {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCategory(Category.FICTION);
        bookshelf.setCapacity(50);

        when(repositoryMock.findBookshelfById(1L)).thenReturn(bookshelf);
        when(repositoryMock.save(any(Bookshelf.class))).thenReturn(bookshelf);

        Bookshelf updatedBookshelf = service.update(1L, 100, Category.NON_FICTION);

        Assertions.assertEquals(100, updatedBookshelf.getCapacity());
        Assertions.assertEquals(Category.NON_FICTION, updatedBookshelf.getCategory());
    }

    @Test
    public void removeBookFromBookshelf_ReturnsTrue_WhenBookExistsAndRemovedSuccessfully()
            throws BookshelfNotFoundException {
        Bookshelf bookshelf = new Bookshelf();
        Book book1 = new Book();
        book1.setTitle("1984");
        bookshelf.getBooks().add(book1);

        when(repositoryMock.findBookshelfById(1L)).thenReturn(bookshelf);
        when(repositoryMock.save(any(Bookshelf.class))).thenReturn(bookshelf);

        boolean result = service.removeBookFromBookshelf(1L, "1984");
        Assertions.assertTrue(result);
    }

    @Test
    public void removeBookFromBookshelf_ReturnsFalse_WhenBookDoesNotExist()
            throws BookshelfNotFoundException {
        Bookshelf bookshelf = new Bookshelf();

        when(repositoryMock.findBookshelfById(1L)).thenReturn(bookshelf);
        when(repositoryMock.save(any(Bookshelf.class))).thenReturn(bookshelf);

        boolean result = service.removeBookFromBookshelf(1L, "Harry Potter");
        Assertions.assertFalse(result);
    }
}
