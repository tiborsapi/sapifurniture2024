package ro.sapientia.furniture.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "furniture_door")
public class FurnitureDoor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_furniture_door")
	@SequenceGenerator(name="pk_furniture_door", sequenceName="pk_furniture_door")
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "width")
	private int width;

	@Column(name = "height")
	private int height;

	@Column(name = "thickness")
	private int thickness;

	@Column(name = "material")
	private String material;

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

	public int getThickness() {
		return thickness;
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Override
	public String toString() {
		return "FurnitureDoor [id=" + id + ", width=" + width + ", height=" + height +
		       ", thickness=" + thickness + ", material=" + material + "]";
	}
}
