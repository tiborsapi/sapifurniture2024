package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.Window;

public interface WindowRepository extends JpaRepository<Window, Long> {

    Window findWindowById(Long id);

}
