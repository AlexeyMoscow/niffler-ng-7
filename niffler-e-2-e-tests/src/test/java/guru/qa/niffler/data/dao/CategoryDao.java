package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.spend.CategoryEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryDao {

  CategoryEntity createCategory(CategoryEntity category);

  CategoryEntity updateCategory(CategoryEntity category);

  Optional<CategoryEntity> findCategoryById(UUID id);

  Optional<CategoryEntity> findCategoryByUsernameAndCategoryName(String username, String categoryName);

  List<CategoryEntity> findAllByUsername(String username);

  void deleteCategory(CategoryEntity category);
}
