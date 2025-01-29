package ro.sapientia.furniture.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = "nightstand")
public class Nightstand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_nightstand")
    @SequenceGenerator(name="pk_nightstand", sequenceName="pk_nightstand")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NonNull
    @Column(name = "width", nullable = false)
    private int width;

    @NonNull
    @Column(name = "height", nullable = false)
    private int height;

    @NonNull
    @Column(name = "depth", nullable = false)
    private int depth;

    @NonNull
    @Column(name = "number_of_drawers", nullable = false)
    private int numberOfDrawers;

    @NonNull
    @Column(name = "has_lamp", nullable = false)
    private boolean hasLamp;

    @NonNull
    @Column(name = "color", nullable = false)
    @Enumerated(EnumType.STRING)
    private NightstandColor color;

}
