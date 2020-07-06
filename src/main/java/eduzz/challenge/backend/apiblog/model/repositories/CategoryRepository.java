package eduzz.challenge.backend.apiblog.model.repositories;

import org.springframework.data.repository.CrudRepository;

import eduzz.challenge.backend.apiblog.model.entities.Category;

/**
 * @author Michell Algarra Barros
 * @version 1.1
 * @since 1.1
 */
public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
