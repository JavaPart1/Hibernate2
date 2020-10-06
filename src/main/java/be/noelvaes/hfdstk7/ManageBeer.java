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

            System.out.println(pgmName + " - Start txn Get all beers");
            tx.begin();
            List<Beers> beers = queryBeers.getResultList();
            tx.commit();

            // Get existing brewers
            TypedQuery<Brewer> queryBrewers = em.createNamedQuery("getAllBrewers", Brewer.class);
            System.out.println(pgmName + " - Start txn GetAllBrewers");
            tx.begin();
            List<Brewer> brewers = queryBrewers.getResultList();
            tx.commit();
            System.out.println(pgmName + " - Commit txn");
            // Get existing categories
            TypedQuery<Categories> queryCategories = em.createNamedQuery("getAllCategories", Categories.class);
            System.out.println(pgmName + " - Start txn GetAllCategories");
            tx.begin();
            List<Categories> categories = queryCategories.getResultList();
            tx.commit();
            System.out.println(pgmName + " - Commit txn");

            // Create a new beer with existing brewer & category
            // Choose category
            Categories blond = beers.stream().filter(b -> b.getCategory().getCatName().equals("Blond")).findFirst().get().getCategory();
            Categories pils = beers.stream().filter(b -> b.getCategory().getCatName().equals("Lager")).findFirst().get().getCategory();
            Categories dark = categories.stream().filter(c -> c.getCatName().equals("Dark")).findFirst().get();
            // Choose brewer
            Brewer westmalle = brewers.stream().filter(br -> br.getBrewerName().contains("Westmalle")).findFirst().get();
            Brewer aBinbev = beers.stream().filter(b -> b.getBrewer().getBrewerName().equals("ABinbev")).findFirst().get().getBrewer();
            // Create Beer
            Beers newBeer = new Beers("Westmalle Trappist",8,1000,6,dark);
            newBeer.setBrewer(westmalle);
            // Update beers in brewer
            westmalle.getBeers().add(newBeer);
            // Update beers in category
            dark.getBeers().add(newBeer);
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
