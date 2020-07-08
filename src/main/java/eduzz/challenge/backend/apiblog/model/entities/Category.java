package eduzz.challenge.backend.apiblog.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * All properties from "Category" object
 * @author Michell Algarra Barros
 * @version 1.1
 * @since 1.0
 */
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	@Column(unique = true)
	private String category;
	
	public Category() {
		
	}

	/**
	 * @param category Category to be registered
	 */
	public Category(@NotBlank String category) {
		super();
		this.category = category;
	}
	
	public int getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
