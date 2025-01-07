package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.Bookshelf;

public interface BookshelfRepository extends JpaRepository<Bookshelf, Long> {

    Bookshelf findBookshelfById(Long id);

}
