package ro.sapientia.furniture.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.Book;
import ro.sapientia.furniture.model.Bookshelf;
import ro.sapientia.furniture.service.BookshelfService;
import ro.sapientia.furniture.util.BookshelfNotFoundException;
import ro.sapientia.furniture.util.Category;
import ro.sapientia.furniture.util.InvalidCategoryException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookshelfController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})

public class BookshelfControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean(BookshelfService.class)
    private BookshelfService service;

    @Test
    public void testGetAllBookshelves() throws Exception {
        final Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCapacity(20);
        bookshelf.setCategory(Category.FICTION);
        when(service.findAllBookshelves()).thenReturn(List.of(bookshelf));

        this.mockMvc.perform(get("/bookshelf/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].capacity", is(20)))
                .andExpect(jsonPath("$[0].category", is("FICTION")));
    }

    @Test
    public void testBookshelfNotFound() throws Exception {
        when(service.findBookshelfById(23L)).thenThrow(new BookshelfNotFoundException("Bookshelf not found."));

        this.mockMvc.perform(get("/bookshelf/find/23"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCountBooksInBookshelf() throws Exception {
        when(service.countBooksInBookshelf(23L)).thenReturn(3);

        this.mockMvc.perform(get("/bookshelf/23/books/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(3)));
    }

    @Test
    public void testSortBooksInBookshelf() throws Exception {
        Book book1 = new Book();
        Book book2 = new Book();
        book1.setTitle("1984");
        book2.setTitle("Harry Potter");

        when(service.sortBooksInBookshelf(23L)).thenReturn(List.of(book1, book2));

        this.mockMvc.perform(get("/bookshelf/23/books/sort"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is("1984")));
    }

    @Test
    public void testBookshelfNotFoundWhenSortingBooks() throws Exception {
        when(service.sortBooksInBookshelf(23L)).thenThrow(new BookshelfNotFoundException("Bookshelf not found."));

        this.mockMvc.perform(get("/bookshelf/23/books/sort"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddBookToBookshelfWithInvalidCategory() throws Exception {
        when(service.addBookToBookshelf(1L, "Title", "Author", Category.SCIENCE))
                .thenThrow(new InvalidCategoryException("Book category does not match bookshelf category."));

        this.mockMvc.perform(post("/bookshelf/1/addBook")
                        .param("title", "Title")
                        .param("author", "Author")
                        .param("category", "SCIENCE"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Book category does not match bookshelf category."));
    }

    @Test
    public void testAddBookToBookshelfSuccessfully() throws Exception {
        when(service.addBookToBookshelf(1L, "Title", "Author", Category.SCIENCE))
                .thenReturn(true);

        this.mockMvc.perform(post("/bookshelf/1/addBook")
                        .param("title", "Title")
                        .param("author", "Author")
                        .param("category", "SCIENCE"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book added successfully."));
    }

    @Test
    public void testCreateBookshelfSuccessfully() throws Exception {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCapacity(10);
        bookshelf.setCategory(Category.HISTORY);
        when(service.create(10, Category.HISTORY))
                .thenReturn(bookshelf);

        this.mockMvc.perform(post("/bookshelf/add")
                        .param("capacity", "10")
                        .param("category", "HISTORY"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.capacity", is(10)))
                .andExpect(jsonPath("$.category", is("HISTORY")));
    }

    @Test
    public void testRemoveBookFromBookshelfNotFound() throws Exception {
        when(service.removeBookFromBookshelf(1L, "1984"))
                .thenThrow(new BookshelfNotFoundException("Bookshelf not found."));

        this.mockMvc.perform(delete("/bookshelf/1/removeBook")
                        .param("title", "1984"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Bookshelf not found."));
    }

    @Test
    public void testUpdateBookshelfSuccessfully() throws Exception {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCapacity(10);
        bookshelf.setCategory(Category.HISTORY);
        when(service.update(23L, 10, Category.HISTORY))
                .thenReturn(bookshelf);

        this.mockMvc.perform(patch("/bookshelf/update/23")
                        .param("capacity", "10")
                        .param("category", "HISTORY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.capacity", is(10)))
                .andExpect(jsonPath("$.category", is("HISTORY")));
    }
}
