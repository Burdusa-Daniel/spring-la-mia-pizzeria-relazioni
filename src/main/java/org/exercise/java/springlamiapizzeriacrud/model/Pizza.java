package org.exercise.java.springlamiapizzeriacrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "this can't be blank")
    @Size(min = 1, max = 30)
    private String name;
    @Size(min = 1, max = 50)
    private String description;
    @Min(1)
    @Max(20)
    private int price;
    @OneToMany(mappedBy = "pizza",cascade = {CascadeType.REMOVE})
    private List<OffertaSpeciale> offertaSpecialeList;
    @ManyToMany
    private List<Ingrediente> ingredienti;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<OffertaSpeciale> getOffertaSpecialeList() {
        return offertaSpecialeList;
    }

    public void setOffertaSpecialeList(List<OffertaSpeciale> offertaSpecialeList) {
        this.offertaSpecialeList = offertaSpecialeList;
    }

    public List<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(List<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public Boolean hasOffer(){
        LocalDate today = LocalDate.now();
        for(OffertaSpeciale o:offertaSpecialeList){
            if (o.getDataInizio().isBefore(today)&&o.getDataFine().isAfter(today)){
                return true;
            }
        }
        return false;
    }
}
