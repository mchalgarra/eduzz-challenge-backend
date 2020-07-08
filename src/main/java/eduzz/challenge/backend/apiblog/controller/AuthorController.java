package eduzz.challenge.backend.apiblog.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eduzz.challenge.backend.apiblog.model.entities.Author;
import eduzz.challenge.backend.apiblog.model.entities.Blog;
import eduzz.challenge.backend.apiblog.model.repositories.AuthorRepository;
import eduzz.challenge.backend.apiblog.model.repositories.BlogRepository;
import eduzz.challenge.backend.apiblog.model.util.ElementById;
import eduzz.challenge.backend.apiblog.model.util.ElementList;

/**
 * Responsible class for mapping it self and methods to answer the HTTP methods
 * @author Michell Algarra Barros
 * @version 1.1
 * @since 1.0
 */
@RestController
@RequestMapping(path = "/blog/author")
public class AuthorController {

	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	BlogRepository blogRepository;
	
	/**
	 * Insert a new author into the database 
	 * @param author Author to be registered
	 * @return Author - Author to be inserted
	 */
	@PostMapping
	public @ResponseBody Author newAuthor(@Valid Author author) {
		authorRepository.save(author);
		return author;
	}
	
	/**
	 * Get all authors and arranges the obtained JSON formated data to be nested
	 * @return Map - Formated data 
	 */
	@GetMapping
	public @ResponseBody Map<String, Map<String, List<Author>>> getAuthor() {
		ElementList<Author> authorList = new ElementList<>();
		return authorList.getList("authors", authorRepository.findAll());
	}
	
	/**
	 * Get author by id and arranges the obtained JSON formated data to be nested
	 * @param id Requested id
	 * @return Map - Formated data 
	 */
	@GetMapping("/{id}")
	public @ResponseBody Map<String, Map<String, List<Author>>> getAuthorById(@PathVariable int id) {
		ElementById<Author> author = new ElementById<>(); 
		return author.getElement("authors", authorRepository.findById(id));
	}
	
	/**
	 * Update an author according to the selected id
	 * @param id Searched id
	 * @param name Author new name
	 * @return ResponseEntity - Successful operation (returns body) or not (not found)
	 */
	@PutMapping
	public @ResponseBody ResponseEntity<?> updateAuthor(@RequestParam int id, @RequestParam String name) {
		return authorRepository.findById(id)
				.map(author -> {
					author.setName(name);
					authorRepository.save(author);
					return ResponseEntity.ok().body(author);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	/**
	 * Delete an author according to the selected id
	 * @param id Searched id
	 * @return ResponseEntity - Successful operation (returns body) or not (not found)
	 */
	@DeleteMapping
	public @ResponseBody String deleteAuthor(@RequestParam int id) {
		boolean authorPost = false;
		Iterable<Blog> posts = blogRepository.findAll();
		
		for (Blog blog : posts) {
			if(blog.getAuthorId() == id) {
				authorPost = true;
				break;
			}
		}
		
		if(authorPost) {
			return "Cannot delete an author that has a post related to him!\n"
					+ "Delete the post so the author can be deleted.";
		}
		
		authorRepository.deleteById(id);
		return "Author successfully deleted!";
	}
}
