package eduzz.challenge.backend.apiblog.model.repositories;

import org.springframework.data.repository.CrudRepository;

import eduzz.challenge.backend.apiblog.model.entities.Blog;

/**
 * @author Michell Algarra Barros
 * @version 1.1
 * @since 1.0
 */
public interface BlogRepository extends CrudRepository<Blog, Integer> {
	
}
