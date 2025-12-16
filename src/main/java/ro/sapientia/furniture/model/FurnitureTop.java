package ro.sapientia.furniture.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "furniture_top")
public class FurnitureTop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_furniture_top")
	@SequenceGenerator(name = "pk_furniture_top", sequenceName = "pk_furniture_top")
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "width")
	private int width;

	@Column(name = "height")
	private int height;

	@Column(name = "depth")
	private int depth;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FurnitureTop [id=" + id + ", width=" + width + ", height=" + height + ", depth=" + depth + "]";
	}
}
