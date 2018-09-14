package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select c.childs from Category c where c.id = ?1")
	List<Category> categoriesByCategoryId(Integer categoryId);

	@Query("select avg(c.visas.size), min(c.visas.size), max(c.visas.size), "
			+ "stddev(c.visas.size) from Category c")
	Double[] visasStadistics();

	@Query("select c.childs from Category c where c.name = 'CATEGORY'")
	List<Category> categoriesOfFirstLevel();

	@Query("select c from Category c where c.name != 'CATEGORY'")
	List<Category> getCategories();
}
