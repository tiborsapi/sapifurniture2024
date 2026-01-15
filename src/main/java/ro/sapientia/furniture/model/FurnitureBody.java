package ro.sapientia.furniture.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "furniture_body")
public class FurnitureBody implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_furniture_body")
	@SequenceGenerator(name="pk_furniture_body",sequenceName="pk_furniture_body") 
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "width")
	private int width;

	@Column(name = "heigth")
	private int heigth;

	@Column(name = "depth")
	private int depth;

	@ManyToOne
	@JoinColumn(name = "version_id")
	@JsonBackReference
	private ProjectVersion version; 

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ProjectVersion getVersion() {
		return version;
	}

	public void setVersion(ProjectVersion version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "FurnitureBody [id=" + id + ", width=" + width + ", heigth=" + heigth + ", depth=" + depth + ", version=" + version + "]";
	}

}
