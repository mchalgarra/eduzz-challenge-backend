package eduzz.challenge.backend.apiblog.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * All properties from "Author" object
 * @author Michell Algarra Barros
 * @version 1.1
 * @since 1.0
 */
@Entity
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank
	@Column(unique = true)
	private String name;
	
	@Min(0)
	@Column(nullable = false)
	private int age;
	
	public Author() {
		
	}

	/**
	 * @param name Author name
	 * @param age Author age
	 */
	public Author(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
}
