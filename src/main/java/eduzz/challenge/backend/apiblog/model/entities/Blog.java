package eduzz.challenge.backend.apiblog.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;

/**
 * All properties from "Blog" object
 * @author Michell Algarra Barros
 * @version 1.1
 * @since 1.0
 */
@Entity
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn(name = "author_id", nullable = false, updatable = true)
	private int authorId;
	
	@JoinColumn(name = "category_id", nullable = false, updatable = true)
	private int categoryId;
	
	@NotBlank
	@Column(unique = true)
	private String title;
	
	@NotBlank
	private String text;
	
	public Blog() {
		
	}

	/**
	 * @param title Post title
	 * @param authorId Post author id
	 * @param categoryId Post category id
	 * @param text Post content
	 */
	public Blog(int authorId, int categoryId, String title, String text) {
		super();
		this.authorId = authorId;
		this.categoryId = categoryId;
		this.title = title;
		this.text = text;
	}
	
	public int getId() {
		return id;
	}
	
	public int getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
