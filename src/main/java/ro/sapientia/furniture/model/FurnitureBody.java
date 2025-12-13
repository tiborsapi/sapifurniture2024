package ro.sapientia.furniture.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "furniture_body")
public class FurnitureBody implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_furniture_body")
	@SequenceGenerator(name="pk_furniture_body",sequenceName="pk_furniture_body") 
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "width")
	private Integer width;

	@Column(name = "heigth")
	private Integer heigth;

	@Column(name = "depth")
	private Integer depth;

	@Column(name = "thickness")
	private Integer thickness;

	@OneToMany(mappedBy = "furnitureBody", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FrontElement> frontElements;

	@OneToOne
	@JoinColumn(name = "main_front_element_id")
	private FrontElement mainFrontElement;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeigth() {
		return heigth;
	}

	public void setHeigth(Integer heigth) {
		this.heigth = heigth;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getThickness() {
		return thickness;
	}

	public void setThickness(Integer thickness) {
		this.thickness = thickness;
	}

	public List<FrontElement> getFrontElements() {
		return frontElements;
	}

	public void setFrontElements(List<FrontElement> frontElements) {
		this.frontElements = frontElements;
	}

	public ComponentList getRawMaterials() {
		refreshMainFrontElement();
		ComponentList componentList = new ComponentList(this, getMainFrontElement().getRawMaterials());
		return componentList;
	}

	public void refreshMainFrontElement() {
		if (mainFrontElement == null) {
			if (frontElements != null && !frontElements.isEmpty()) {
				for (FrontElement fe : frontElements) {
					if (fe.getElementType() == FrontElement.ElementType.BODY) {
						mainFrontElement = fe;
						return;
					}
				}
			} else {
				mainFrontElement = new FrontElement(this, FrontElement.ElementType.BODY, 0, 0, width, heigth);
			}
		}
	}

	public FrontElement getMainFrontElement() {
		return mainFrontElement;
	}

	public void setMainFrontElement(FrontElement mainFrontElement) {
		this.mainFrontElement = mainFrontElement;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FurnitureBody [id=" + id + ", width=" + width + ", heigth=" + heigth + ", depth=" + depth + ", thickness=" + thickness + ", mainFrontElement=" + (mainFrontElement != null ? mainFrontElement.getId() : "null") + "]";
	}

}
