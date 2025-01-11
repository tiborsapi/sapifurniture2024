package ro.sapientia.furniture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.furniture.model.Book;
import ro.sapientia.furniture.model.Bookshelf;
import ro.sapientia.furniture.service.BookshelfService;
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
        final Bookshelf bookshelf = bookshelfService.findBookshelfById(id);
        return new ResponseEntity<>(bookshelf, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Bookshelf> addBookshelf(@RequestBody Bookshelf bookshelf) {
        final Bookshelf persistentBookshelf = bookshelfService.create(bookshelf);
        return new ResponseEntity<>(persistentBookshelf, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Bookshelf> updateBookshelf(@RequestBody Bookshelf bookshelf) {
        final Bookshelf persistentBookshelf = bookshelfService.update(bookshelf);
        return new ResponseEntity<>(persistentBookshelf, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
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
        } catch (InvalidCategoryException | InsufficientCapacityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/removeBook")
    public ResponseEntity<String> removeBookFromBookshelf(
            @PathVariable("id") Long bookshelfId,
            @RequestParam String title
    ) {
        boolean removed = bookshelfService.removeBookFromBookshelf(bookshelfId, title);
        if (removed) {
            return new ResponseEntity<>("Book removed successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/books/sort")
    public ResponseEntity<List<Book>> sortBooksInBookshelf(@PathVariable("id") Long bookshelfId) {
        List<Book> sortedBooks = bookshelfService.sortBooksInBookshelf(bookshelfId);
        if (sortedBooks != null) {
            return new ResponseEntity<>(sortedBooks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/books/count")
    public ResponseEntity<Integer> countBooksInBookshelf(@PathVariable("id") Long bookshelfId) {
        int count = bookshelfService.countBooksInBookshelf(bookshelfId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
