package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Book {
	 @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

     @NotBlank
     private String title;

     @NotNull
     @Min(1891)
     @Max(2025)
     private Integer year;
     
     
     private String urlImage;
     
     @ManyToMany(mappedBy = "books")
     private List<Author> authors;
     
     @OneToMany(mappedBy = "book")
     private List<Review> reviews;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authors, id, title, urlImage, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(authors, other.authors) && Objects.equals(id, other.id)
				&& Objects.equals(title, other.title) && Objects.equals(urlImage, other.urlImage)
				&& Objects.equals(year, other.year);
	}
	
	public void addAuthor(Author author) {
	    if (!authors.contains(author)) {
	        authors.add(author);
	    }
	}

	public void removeAuthor(Author author) {
	    authors.remove(author);
	}
	
}
