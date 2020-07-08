package eduzz.challenge.backend.apiblog.controller;

import java.util.List;
import java.util.Map;

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

import eduzz.challenge.backend.apiblog.model.entities.Blog;
import eduzz.challenge.backend.apiblog.model.repositories.AuthorRepository;
import eduzz.challenge.backend.apiblog.model.repositories.BlogRepository;
import eduzz.challenge.backend.apiblog.model.repositories.CategoryRepository;
import eduzz.challenge.backend.apiblog.model.util.ElementById;
import eduzz.challenge.backend.apiblog.model.util.ElementList;

/**
 * Responsible class for mapping it self and methods to answer the HTTP methods
 * @author Michell Algarra Barros
 * @version 1.1
 * @since 1.0
 */
@RestController
@RequestMapping(path = "/blog")
public class BlogController {

	@Autowired
	public BlogRepository blogRepository;

	@Autowired
	public AuthorRepository authorRepository;
	
	@Autowired
	public CategoryRepository categoryRepository;
	
	/**
	 * Insert a new post into the database according to existing authors and categories 
	 * @param title Post title
	 * @param authorId Id of the registered author
	 * @param categoryId Id of the registered category
	 * @param text Post content
	 * @return ResponseEntity - Post to be inserted or error message
	 */
	@PostMapping
	public @ResponseBody ResponseEntity<?> newPost(
			@RequestParam int authorId,
			@RequestParam int categoryId,
			@RequestParam String title,
			@RequestParam String text) {
		
		boolean authorExists = authorRepository.existsById(authorId);
		boolean categoryExists = categoryRepository.existsById(categoryId);
		
		if(authorExists && categoryExists) {
			Blog post = new Blog(authorId, categoryId, title, text);
			blogRepository.save(post);
			return ResponseEntity.ok().body(post);
		}
		return ResponseEntity.ok().body("Author or category does not exists! Try again.");
	}
	
	/**
	 * Get all posts and arranges the obtained JSON formated data to be nested
	 * @return Map - Formated data 
	 */
	@GetMapping
	public @ResponseBody Map<String, Map<String, List<Blog>>> getPost() {
		ElementList<Blog> blogList = new ElementList<>();
		return blogList.getList("posts", blogRepository.findAll());
	}
	
	/**
	 * Get post by id and arranges the obtained JSON formated data to be nested
	 * @param id Requested id
	 * @return Map - Formated data 
	 */
	@GetMapping("/{id}")
	public @ResponseBody Map<String, Map<String, List<Blog>>> getPostById(@PathVariable int id) {
		ElementById<Blog> post = new ElementById<>(); 
		return post.getElement("posts", blogRepository.findById(id));
	}
	
	/**
	 * Update a post according to the selected id
	 * @param id Searched id
	 * @param title New post title
	 * @param categoryId The new post category id
	 * @param text New post content
	 * @return ResponseEntity - Successful operation (returns body) or not (not found)
	 */
	@PutMapping
	public @ResponseBody ResponseEntity<?> updatePost(
			@RequestParam int id,
			@RequestParam int categoryId,
			@RequestParam String title,
			@RequestParam String text) {
		if(categoryRepository.existsById(categoryId)) {
			return blogRepository.findById(id)
					.map(post -> {
						post.setTitle(title);
						post.setCategoryId(categoryId);
						post.setText(text);
						Blog updated = blogRepository.save(post);
						return ResponseEntity.ok().body(updated);
					}).orElse(ResponseEntity.ok().build());
		}
		return ResponseEntity.ok().body("Post does not exists! Try again.");
	}
	
	/**
	 * Delete one registry of the database
	 * @param id Searched id
	 * @return String - Successfully deleted
	 */
	@DeleteMapping
	public @ResponseBody String deletePost(@RequestParam int id) {
		blogRepository.deleteById(id);
		return "Post successfully deleted";
	}
}
