package be.noelvaes.hfdstk7;

import be.noelvaes.hfdstk7.domain.Categories;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

public class DeleteCategory {
    public static void main(String[] args) {
        String pgmName = DeleteCategory.class.getSimpleName();
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
            String delCatName = "Dark";
            // Check if category exists
            if (categoriesList.stream().anyMatch(c -> c.getCatName().equals(delCatName))) {
                // new category exists
                Categories delCategory = categoriesList.stream().filter(c -> c.getCatName().equals(delCatName)).findFirst().get();
                System.out.println(pgmName + " - Start txn Delete category");
                tx.begin();
                em.remove(delCategory);
                tx.commit();
                System.out.println(pgmName + " - Commit txn");
            } else {
                System.out.println("Category " + delCatName + " does not exists");
            }
            System.out.println(pgmName + " - End of program");
        } catch (Exception exception){
            System.out.println(exception.getMessage().toString());
            System.out.println("Delete category is not executed");
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }

    }
}
