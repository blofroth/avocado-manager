package se.ffcg.gkelunch;

import java.time.LocalDate;

public class Avocado {

    public String id;
    public Integer ripeness;
    public LocalDate dateOfPurchase;
    public String breed;

    public Avocado(String id, Integer ripeness, LocalDate dateOfPurchase, String breed) {
        this.id = id;
        this.ripeness = ripeness;
        this.dateOfPurchase = dateOfPurchase;
        this.breed = breed;
    }
}