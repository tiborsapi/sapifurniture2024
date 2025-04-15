package ro.sapientia.furniture.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "hammock_body")
public class HammockBody implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_hammock_body")
    @SequenceGenerator(name = "pk_hammock_body", sequenceName = "pk_hammock_body", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "length")
    private double length;

    @Column(name = "width")
    private double width;

    @Column(name = "material")
    private String material;

    @Column(name = "weight")
    private double weight;
}
