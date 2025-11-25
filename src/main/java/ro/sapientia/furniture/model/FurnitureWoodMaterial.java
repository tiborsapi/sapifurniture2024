package ro.sapientia.furniture.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wood_materials")
public class WoodMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private String category;//(pl. "tömörfa", "forgácslap", "rétegelt lemez")

    private double thicknessMm;

    private double pricePerSquareMeter;

    private double densityKgPerM2;

    private String surfaceFinish;//(lakkozott / festett / nyers / matt)

    public WoodMaterial() {}

    public WoodMaterial(String code, String name, String category, double thicknessMm,
                        double pricePerSquareMeter, double densityKgPerM2,
                        String surfaceFinish) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.thicknessMm = thicknessMm;
        this.pricePerSquareMeter = pricePerSquareMeter;
        this.densityKgPerM2 = densityKgPerM2;
        this.surfaceFinish = surfaceFinish;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getThicknessMm() {
        return thicknessMm;
    }

    public void setThicknessMm(double thicknessMm) {
        this.thicknessMm = thicknessMm;
    }

    public double getPricePerSquareMeter() {
        return pricePerSquareMeter;
    }

    public void setPricePerSquareMeter(double pricePerSquareMeter) {
        this.pricePerSquareMeter = pricePerSquareMeter;
    }

    public double getDensityKgPerM2() {
        return densityKgPerM2;
    }

    public void setDensityKgPerM2(double densityKgPerM2) {
        this.densityKgPerM2 = densityKgPerM2;
    }

    public String getSurfaceFinish() {
        return surfaceFinish;
    }

    public void setSurfaceFinish(String surfaceFinish) {
        this.surfaceFinish = surfaceFinish;
    }
}
