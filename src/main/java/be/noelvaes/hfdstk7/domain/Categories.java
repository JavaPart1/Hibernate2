package be.noelvaes.hfdstk7.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "getAllCategories",query = "select c from Categories as c")
@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int catId;
    private String catName;

    @OneToMany(mappedBy = "category")
    private List<Beers> beers = new ArrayList<>();

    public Categories() {
    }

    public Categories(String catName) {
        this.catName = catName;
    }

    public int getCatId() {
        return catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public List<Beers> getBeers() {
        return beers;
    }

    public void setBeers(List<Beers> beers) {
        this.beers = beers;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "catId=" + catId +
                ", catName='" + catName + '\'' +
                '}';
    }
}
