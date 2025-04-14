package ro.sapientia.furniture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.furniture.model.Book;
import ro.sapientia.furniture.model.Bookshelf;
import ro.sapientia.furniture.service.BookshelfService;
import ro.sapientia.furniture.util.BookshelfNotFoundException;
import ro.sapientia.furniture.util.Category;
import ro.sapientia.furniture.util.InsufficientCapacityException;
import ro.sapientia.furniture.util.InvalidCategoryException;

import java.util.List;

@RestController
@RequestMapping("/bookshelf")
public class BookshelfController {
    private final BookshelfService bookshelfService;

    public BookshelfController(final BookshelfService bookshelfService) {
        this.bookshelfService = bookshelfService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bookshelf>> getAllBookshelves() {
        final List<Bookshelf> bookshelfList = bookshelfService.findAllBookshelves();
        return new ResponseEntity<>(bookshelfList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Bookshelf> getBookshelfById(@PathVariable("id") Long id) {
        try {
            final Bookshelf bookshelf = bookshelfService.findBookshelfById(id);
            return new ResponseEntity<>(bookshelf, HttpStatus.OK);
        } catch (BookshelfNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Bookshelf> addBookshelf(@RequestParam int capacity,
                                                  @RequestParam Category category) {
        final Bookshelf persistentBookshelf = bookshelfService.create(capacity, category);
        return new ResponseEntity<>(persistentBookshelf, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Bookshelf> updateBookshelf(@PathVariable("id") Long bookshelfId,
                                                     @RequestParam int capacity,
                                                     @RequestParam Category category) {
        try {
            final Bookshelf persistentBookshelf = bookshelfService.update(bookshelfId, capacity, category);
            return new ResponseEntity<>(persistentBookshelf, HttpStatus.OK);
        } catch (BookshelfNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookshelfById(@PathVariable("id") Long id) {
        bookshelfService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/addBook")
    public ResponseEntity<String> addBookToBookshelf(
            @PathVariable("id") Long bookshelfId,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam Category category
    ) {
        try {
            bookshelfService.addBookToBookshelf(bookshelfId, title, author, category);
            return new ResponseEntity<>("Book added successfully.", HttpStatus.OK);
        } catch (InvalidCategoryException | InsufficientCapacityException | BookshelfNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/removeBook")
    public ResponseEntity<String> removeBookFromBookshelf(
            @PathVariable("id") Long bookshelfId,
            @RequestParam String title
    ) {
        try {
            boolean removed = bookshelfService.removeBookFromBookshelf(bookshelfId, title);
            return new ResponseEntity<>("Book removed successfully.", HttpStatus.OK);
        } catch (BookshelfNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/books/sort")
    public ResponseEntity<List<Book>> sortBooksInBookshelf(@PathVariable("id") Long bookshelfId) {
        try {
            List<Book> sortedBooks = bookshelfService.sortBooksInBookshelf(bookshelfId);
            return new ResponseEntity<>(sortedBooks, HttpStatus.OK);
        } catch (BookshelfNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/books/count")
    public ResponseEntity<Integer> countBooksInBookshelf(@PathVariable("id") Long bookshelfId) {
        try {
            int count = bookshelfService.countBooksInBookshelf(bookshelfId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (BookshelfNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
