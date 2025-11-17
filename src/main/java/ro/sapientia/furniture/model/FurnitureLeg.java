package ro.sapientia.furniture.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "furniture_leg")
public class FurnitureLeg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_furniture_leg")
	@SequenceGenerator(name = "pk_furniture_leg", sequenceName = "pk_furniture_leg")
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "length")
	private int length;

	@Column(name = "diameter")
	private int diameter;

	@Column(name = "material")
	private String material;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Override
	public String toString() {
		return "FurnitureLeg [id=" + id + ", length=" + length + ", diameter=" + diameter + ", material=" + material + "]";
	}
}
