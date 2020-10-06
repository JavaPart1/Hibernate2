package be.noelvaes.hfdstk7;

import be.noelvaes.hfdstk7.domain.Beers;
import be.noelvaes.hfdstk7.domain.Brewer;
import be.noelvaes.hfdstk7.domain.Categories;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class ManageBeer {
    public static void main(String[] args) {
        String pgmName = ManageBeer.class.getSimpleName();
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            Scanner input = new Scanner(System.in);
            int choice;
            emf = Persistence.createEntityManagerFactory("mysqlcontainer");
            em = emf.createEntityManager();
            EntityTransaction tx =em.getTransaction();
            // Get existing beers, brewers & categories
            TypedQuery<Beers> queryBeers = em.createNamedQuery("getAllBeers", Beers.class);

            System.out.println(pgmName + " - Start txn");
            tx.begin();
            List<Beers> beers = queryBeers.getResultList();
            tx.commit();

//            // Get existing brewers
//            TypedQuery<Brewer> queryBrewers = em.createNamedQuery("getAllBrewers", Brewer.class);
//            System.out.println(pgmName + " - Start txn GetAllBrewers");
//            tx.begin();
//            List<Brewer> brewers = queryBrewers.getResultList();
//            tx.commit();
//            System.out.println(pgmName + " - Commit txn");
//            // Get categories
//            TypedQuery<Categories> queryCategories = em.createNamedQuery("getAllCategories", Categories.class);
//            System.out.println(pgmName + " - Start txn GetAllCategories");
//            tx.begin();
//            List<Categories> categories = queryCategories.getResultList();
//            tx.commit();
//            System.out.println(pgmName + " - Commit txn");

            // Create a new beer with existing brewer & category
            // Choose category
            Categories blond = beers.stream().filter(b -> b.getCategory().getCatName().equals("Blond")).findFirst().get().getCategory();
            Categories pils = beers.stream().filter(b -> b.getCategory().getCatName().equals("Lager")).findFirst().get().getCategory();
            // Choose brewer
            Brewer aBinbev = beers.stream().filter(b -> b.getBrewer().getBrewerName().equals("ABinbev")).findFirst().get().getBrewer();
            // Create Beer
            Beers newBeer = new Beers("Gouden Carolus",4,5000,4,blond);
            newBeer.setBrewer(aBinbev);
            // Update beers in brewer
            aBinbev.getBeers().add(newBeer);
            // Update beers in category
            pils.getBeers().add(newBeer);
            System.out.println(pgmName + " - Start txn Create beer");
            tx.begin();
            em.persist(newBeer);
            tx.commit();
            System.out.println(pgmName + " - Commit txn");
            System.out.println("Press any number and enter to continue !");
            choice = input.nextInt();

            // Update new beer
            newBeer.setStock(5500);
            System.out.println(pgmName + " - Start txn Update beer");
            tx.begin();
            tx.commit();
            System.out.println(pgmName + " - Commit txn");

            System.out.println("Press any number and enter to continue !");
            choice = input.nextInt();
            // Delete the new beer
            System.out.println(pgmName + " - Start txn Delete beer");
            tx.begin();
            em.remove(newBeer);
            tx.commit();
            System.out.println(pgmName + " - Commit txn");

            System.out.println(pgmName + " - End of program");
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }

    }
}
