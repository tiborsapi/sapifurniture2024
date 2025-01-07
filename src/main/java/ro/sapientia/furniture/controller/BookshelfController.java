package ro.sapientia.furniture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.furniture.model.Bookshelf;
import ro.sapientia.furniture.service.BookshelfService;

import java.util.List;

@RestController
@RequestMapping("/bookshelf")
public class BookshelfController {
    private final BookshelfService bookshelfService;

    public BookshelfController(final BookshelfService bookshelfService) {
        this.bookshelfService = bookshelfService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bookshelf>> getAllBookshelves(){
        final List<Bookshelf> bookshelfList = bookshelfService.findAllBookshelves();
        return new ResponseEntity<>(bookshelfList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Bookshelf> getBookshelfById(@PathVariable("id") Long id){
        final Bookshelf bookshelf = bookshelfService.findBookshelfById(id);
        return new ResponseEntity<>(bookshelf,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Bookshelf> addBookshelf(@RequestBody Bookshelf bookshelf){
        final Bookshelf persistentBookshelf = bookshelfService.create(bookshelf);
        return new ResponseEntity<>(persistentBookshelf,HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Bookshelf> updateBookshelf(@RequestBody Bookshelf bookshelf){
        final Bookshelf persistentBookshelf = bookshelfService.update(bookshelf);
        return new ResponseEntity<>(persistentBookshelf,HttpStatus.OK);
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteBookshelfById(@PathVariable("id") Long id){
        bookshelfService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
