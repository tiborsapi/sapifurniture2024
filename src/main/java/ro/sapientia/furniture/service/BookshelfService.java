package ro.sapientia.furniture.service;

import org.springframework.stereotype.Service;
import ro.sapientia.furniture.model.Bookshelf;
import ro.sapientia.furniture.repository.BookshelfRepository;

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

    public Bookshelf findBookshelfById(final Long id) {
        return this.bookshelfRepository.findBookshelfById(id);
    }

    public Bookshelf create(Bookshelf furnitureBody) {
        return this.bookshelfRepository.saveAndFlush(furnitureBody);
    }

    public Bookshelf update(Bookshelf furnitureBody) {
        return this.bookshelfRepository.saveAndFlush(furnitureBody);
    }

    public void delete(Long id) {
        this.bookshelfRepository.deleteById(id);
    }
}
