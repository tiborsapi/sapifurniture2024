package ro.sapientia.furniture.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * JPA Entity representing a furniture body in the database.
 */
@Entity(name = "furniture_body")
public class FurnitureBody implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_furniture_body")
	@SequenceGenerator(name="pk_furniture_body", sequenceName="pk_furniture_body")
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "width")
	private int width;

	@Column(name = "height")
	private int height;

	@Column(name = "depth")
	private int depth;

	public FurnitureBody() {
	}

	public FurnitureBody(Long id, int width, int height, int depth) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

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

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "FurnitureBody [id=" + id + ", width=" + width + ", heigth=" + height + ", depth=" + depth + "]";
	}
}

