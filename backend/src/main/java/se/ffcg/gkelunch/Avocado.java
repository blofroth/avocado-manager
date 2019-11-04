package se.ffcg.gkelunch;

import java.time.LocalDate;

public class Avocado {

    public Integer ripeness;
    public LocalDate dateOfPurchase;
    public String breed;

    public Avocado(Integer ripeness, LocalDate dateOfPurchase, String breed) {
        this.ripeness = ripeness;
        this.dateOfPurchase = dateOfPurchase;
        this.breed = breed;
    }
}