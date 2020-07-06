package eduzz.challenge.backend.apiblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import eduzz.challenge.backend.apiblog.model.entities.Category;
import eduzz.challenge.backend.apiblog.model.repositories.BlogRepository;
import eduzz.challenge.backend.apiblog.model.repositories.CategoryRepository;

/**
 * Responsible class for mapping it self and methods to answer spring
 * @author Michell Algarra Barros
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping(path = "/blog/category")
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	BlogRepository blogRepository;
	
	/**
	 * Insert a new category into the database 
	 * @param category Category to be registered
	 * @return Category - Category to be inserted
	 */
	@PostMapping
	public @ResponseBody Category newCategory(Category category) {
		categoryRepository.save(category);
		return category;
	}
	
	/**
	 * Get all categories and arranges the obtained JSON formated data to be nested
	 * @return Map - Formated data 
	 */
	@GetMapping
	public @ResponseBody Map<String, Map<String, List<Category>>> getCategory() {
		Map<String, Map<String, List<Category>>> data = new HashMap<>();
		Map<String, List<Category>> categoryMap = new HashMap<>();
		Iterable<Category> category = categoryRepository.findAll();
		
		List<Category> list = StreamSupport.stream(category.spliterator(), false).collect(Collectors.toList());
		
		categoryMap.put("categories", list);
		data.put("data", categoryMap);
		
		return data;
	}
	
	/**
	 * Get category by id and arranges the obtained JSON formated data to be nested
	 * @param id Requested id
	 * @return Map - Formated data
	 */
	@GetMapping("/{id}")
	public @ResponseBody Map<String, Map<String, List<Category>>> getCategoryById(@PathVariable int id) {
		Map<String, Map<String, List<Category>>> data = new HashMap<>();
		Map<String, List<Category>> categoryMap = new HashMap<>();
		List<Category> category = categoryRepository.findById(id).stream().collect(Collectors.toList());
		
		categoryMap.put("categories", category);
		data.put("data", categoryMap);
		
		return data;
	}
	
	/**
	 * Update a category according to the selected id
	 * @param id Searched id
	 * @param category New category
	 * @return ResponseEntity - Successful operation (returns body) or not (not found)
	 */
	@PutMapping
	public @ResponseBody ResponseEntity<?> updateCategory(@RequestParam int id, @RequestParam String category) {
		return categoryRepository.findById(id)
				.map(newCategory -> {
					newCategory.setCategory(category);
					categoryRepository.save(newCategory);
					return ResponseEntity.ok().body(newCategory);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	/**
	 * Delete a category according to the selected id
	 * @param id Searched id
	 * @return ResponseEntity - Successful operation (returns body) or not (not found)
	 */
	@DeleteMapping
	public @ResponseBody String deleteCategory(@RequestParam int id) {
		boolean categoryPost = false;
		Iterable<Blog> posts = blogRepository.findAll();
		
		for (Blog blog : posts) {
			if(blog.getCategoryId() == id) {
				categoryPost = true;
				break;
			}
		}
		
		if(categoryPost) {
			return "Cannot delete a category that has a post related to it!\n"
					+ "Delete the post so the category can be deleted.";
		}
		
		categoryRepository.deleteById(id);
		return "Category successfully deleted!";
	}
}
