package ro.sapientia.furniture.component;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import ro.sapientia.furniture.model.Bookshelf;
import ro.sapientia.furniture.repository.BookshelfRepository;
import ro.sapientia.furniture.util.Category;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookshelfComponentTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookshelfRepository bookshelfRepository;

    @Test
    public void shouldGetAllBookshelves() throws Exception {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCapacity(10);
        bookshelf.setCategory(Category.FICTION);
        bookshelfRepository.saveAndFlush(bookshelf);

        this.mockMvc.perform(get("/bookshelf/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].capacity", is(10)))
                .andExpect(jsonPath("$[0].category", is("FICTION")));
    }

    @Test
    public void shouldGetBookshelfById() throws Exception {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCapacity(5);
        bookshelf.setCategory(Category.NON_FICTION);
        Bookshelf savedBookshelf = bookshelfRepository.saveAndFlush(bookshelf);

        this.mockMvc.perform(get("/bookshelf/find/" + savedBookshelf.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.capacity", is(5)))
                .andExpect(jsonPath("$.category", is("NON_FICTION")));
    }

    @Test
    public void shouldCreateBookshelf() throws Exception {
        this.mockMvc.perform(post("/bookshelf/add")
                        .param("capacity", "15")
                        .param("category", "SCIENCE"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.capacity", is(15)))
                .andExpect(jsonPath("$.category", is("SCIENCE")));
    }

    @Test
    public void shouldUpdateBookshelf() throws Exception {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCapacity(8);
        bookshelf.setCategory(Category.HISTORY);
        Bookshelf savedBookshelf = bookshelfRepository.saveAndFlush(bookshelf);

        this.mockMvc.perform(patch("/bookshelf/update/" + savedBookshelf.getId())
                        .param("capacity", "12")
                        .param("category", "CHILDREN"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.capacity", is(12)))
                .andExpect(jsonPath("$.category", is("CHILDREN")));
    }

    @Test
    public void shouldDeleteBookshelfById() throws Exception {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCapacity(6);
        bookshelf.setCategory(Category.CHILDREN);
        Bookshelf savedBookshelf = bookshelfRepository.saveAndFlush(bookshelf);

        this.mockMvc.perform(delete("/bookshelf/delete/" + savedBookshelf.getId()))
                .andExpect(status().isOk());
    }
}
