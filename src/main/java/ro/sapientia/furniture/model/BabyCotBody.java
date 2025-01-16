package ro.sapientia.furniture.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "baby_cot")
@Table(name = "baby_cot")
public class BabyCotBody implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_baby_cot_body")
	@SequenceGenerator(name="pk_baby_cot_body",sequenceName="pk_baby_cot_body") 
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "width")
	private int width;

	@Column(name = "heigth")
	private int heigth;

	@Column(name = "depth")
	private int depth;
	
	@Column(name = "maxAge")
	private int maxAge;
	
	@Column(name = "rating")
	private int rating;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "BabyCotBody [id=" + id + ", width=" + width + 
				", heigth=" + heigth + ", depth=" + depth + 
				", maxAge=" + maxAge + ", rating=" + rating + "]";
	}

}
