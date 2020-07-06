package eduzz.challenge.backend.apiblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

/**
 * Responsible class for mapping it self and methods to answer spring
 * @author Michell Algarra Barros
 * @version 1.0
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
		Map<String, Map<String, List<Author>>> data = new HashMap<>();
		Map<String, List<Author>> authorMap = new HashMap<>();
		Iterable<Author> author = authorRepository.findAll();
		
		List<Author> list = StreamSupport.stream(author.spliterator(), false).collect(Collectors.toList());
		
		authorMap.put("authors", list);
		data.put("data", authorMap);
		
		return data;
	}
	
	/**
	 * Get author by id and arranges the obtained JSON formated data to be nested
	 * @param id Requested id
	 * @return Map - Formated data 
	 */
	@GetMapping("/{id}")
	public @ResponseBody Map<String, Map<String, List<Author>>> getAuthorById(@PathVariable int id) {
		Map<String, Map<String, List<Author>>> data = new HashMap<>();
		Map<String, List<Author>> authorMap = new HashMap<>();
		List<Author> author = authorRepository.findById(id).stream().collect(Collectors.toList());
		
		authorMap.put("authors", author);
		data.put("data", authorMap);
		
		return data;
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
