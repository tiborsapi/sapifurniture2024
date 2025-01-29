package ro.sapientia.furniture.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "halltree_body")
public class HallTreeBody implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_halltree_body")
	@SequenceGenerator(name="pk_halltree_body",sequenceName="pk_halltree_body") 
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "width")
	private int width;
	
	@Column(name = "heigth")
	private int heigth;
	
	@Column(name = "depth")
	private int depth;
	
	@Column(name = "hangers")
	private int hangers;
	
	@Column(name = "hangedClothes")
	private int hangedClothes;
	
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
	
	public int getHangers() {
		return hangers;
	}
	
	public void setHangers(int hangers) {
		this.hangers = hangers;
	}
	
	public int getHangedClothes() {
		return hangedClothes;
	}
	
	public void setHangedClothes(int hangedClothes) {
		this.hangedClothes = hangedClothes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "HallTreeBody [id=" + id + ", width=" + width + ", heigth=" + heigth + ", depth=" + depth + ", hangers=" + hangers + ", hanged clothes=" + hangedClothes + "]";
	}
	
}
