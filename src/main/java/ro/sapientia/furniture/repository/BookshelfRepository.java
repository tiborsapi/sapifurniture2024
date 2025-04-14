package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.Bookshelf;
import ro.sapientia.furniture.util.Category;

import java.util.ArrayList;

public interface BookshelfRepository extends JpaRepository<Bookshelf, Long> {

    Bookshelf findBookshelfById(Long id);
    ArrayList<Bookshelf> findBookshelfByCategory(Category category);
    ArrayList<Bookshelf> findBookshelfByCapacity(int capacity);
}
