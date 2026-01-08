package br.csi.politecnico.financecontrol.repository;

import br.csi.politecnico.financecontrol.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByUser_Uuid(UUID userUuid);

    @Query(nativeQuery = true, value = """
        SELECT * FROM category c WHERE c.user_id = :userId AND c.user_id IS NULL
    """)
    Category findCategoryExistsPerUser(Long userId, String categoryName);
}
