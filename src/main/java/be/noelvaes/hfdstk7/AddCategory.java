package be.noelvaes.hfdstk7;

import be.noelvaes.hfdstk7.domain.Beers;
import be.noelvaes.hfdstk7.domain.Categories;

import javax.persistence.*;
import java.util.List;

public class AddCategory {
    public static void main(String[] args) {
        String pgmName = AddCategory.class.getSimpleName();
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("mysqlcontainer");
            em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            // Get existing categories
            TypedQuery<Categories> queryCat = em.createNamedQuery("getAllCategories", Categories.class);
            System.out.println(pgmName + " - Start txn Get all categories");
            tx.begin();
            List<Categories> categoriesList = queryCat.getResultList();
            tx.commit();

            // Domain creations
            // Categories
            String newCategory = "Dark";
            // Check if new category exists
            if (categoriesList.stream().anyMatch(c -> c.getCatName().equals(newCategory))) {
                // new category already exists
                System.out.println("New category " + newCategory + " already exists");
            } else {
                Categories dark = new Categories("Dark");
                System.out.println(pgmName + " - Start txn Create new category");
                tx.begin();
                em.persist(dark);
                tx.commit();
                System.out.println(pgmName + " - Commit txn");
            }
            System.out.println(pgmName + " - End of program");
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }

    }
}
