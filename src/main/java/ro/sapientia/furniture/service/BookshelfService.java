package ro.sapientia.furniture.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sapientia.furniture.model.Book;
import ro.sapientia.furniture.model.Bookshelf;
import ro.sapientia.furniture.repository.BookshelfRepository;
import ro.sapientia.furniture.util.BookshelfNotFoundException;
import ro.sapientia.furniture.util.Category;
import ro.sapientia.furniture.util.InsufficientCapacityException;
import ro.sapientia.furniture.util.InvalidCategoryException;

import java.util.List;

@Service
public class BookshelfService {

    private final BookshelfRepository bookshelfRepository;

    public BookshelfService(final BookshelfRepository bookshelfRepository) {
        this.bookshelfRepository = bookshelfRepository;
    }

    public List<Bookshelf> findAllBookshelves() {
        return this.bookshelfRepository.findAll();
    }

    public Bookshelf findBookshelfById(final Long id) throws BookshelfNotFoundException {
        Bookshelf bookshelf = bookshelfRepository.findBookshelfById(id);
        if(bookshelf == null){throw new BookshelfNotFoundException("Bookshelf not found.");}
        return bookshelf;
    }

    public Bookshelf create(int capacity, Category category) {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCapacity(capacity);
        bookshelf.setCategory(category);
        return this.bookshelfRepository.saveAndFlush(bookshelf);
    }

    public Bookshelf update(Long bookshelfId, int capacity, Category category) throws BookshelfNotFoundException {
        Bookshelf bookshelf = this.findBookshelfById(bookshelfId);
        bookshelf.setCapacity(capacity);
        bookshelf.setCategory(category);
        return this.bookshelfRepository.save(bookshelf);
    }

    public void delete(Long id) {
        this.bookshelfRepository.deleteById(id);
    }

    @Transactional
    public boolean addBookToBookshelf(Long bookshelfId, String title, String author, Category bookCategory) throws InsufficientCapacityException, InvalidCategoryException, BookshelfNotFoundException {
        Bookshelf bookshelf = this.findBookshelfById(bookshelfId);
        if (!bookCategory.equals(bookshelf.getCategory())) {
            throw new InvalidCategoryException(
                    "Book category " + bookCategory + " does not match bookshelf category " + bookshelf.getCategory() + "."
            );
        }

        if (bookshelf.getBooks().size() >= bookshelf.getCapacity()) {
            throw new InsufficientCapacityException(
                    "Bookshelf is full. Maximum capacity is " + bookshelf.getCapacity() + "."
            );
        }

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(bookCategory);
        bookshelf.getBooks().add(book);
        this.bookshelfRepository.save(bookshelf);
        return true;
    }

    @Transactional
    public boolean removeBookFromBookshelf(Long bookshelfId, String title) throws BookshelfNotFoundException {
        Bookshelf bookshelf = this.findBookshelfById(bookshelfId);
        if (bookshelf != null) {
            boolean removed = bookshelf.getBooks().removeIf(book -> book.getTitle().equals(title));
            if (removed) {
                this.bookshelfRepository.save(bookshelf);
            }
            return removed;
        }
        return false;
    }

    public List<Book> sortBooksInBookshelf(Long bookshelfId) throws BookshelfNotFoundException {
        Bookshelf bookshelf = this.findBookshelfById(bookshelfId);
        if (bookshelf != null) {
            List<Book> books = bookshelf.getBooks();
            books.sort((b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()));
            return books;
        }
        return null;
    }

    public int countBooksInBookshelf(Long bookshelfId) throws BookshelfNotFoundException {
        Bookshelf bookshelf = this.findBookshelfById(bookshelfId);
        if(bookshelf == null){throw new BookshelfNotFoundException("Bookshelf not found.");}
        return bookshelf.getBooks().size();
    }
}
