package com.graphenedb.support.domain;

import com.graphenedb.support.GDBSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class MovieTest {

    SessionFactory sessionFactory;

    public void getSessionFactory() {
        if(sessionFactory == null) {
            Configuration configuration = new Configuration();
            String boltUri = "bolt://127.0.0.1:7687";
            String boltEncryption = "NONE";

            configuration.driverConfiguration()
                    .setDriverClassName("org.neo4j.ogm.drivers.bolt.driver.BoltDriver")
                    .setURI(boltUri)
                    .setEncryptionLevel(boltEncryption);

            sessionFactory = new SessionFactory(configuration, "com.graphenedb.support.domain");
        }
    }


    @Before
    public void initialize() {
        getSessionFactory();
        Session session = sessionFactory.openSession();
        session.deleteAll(Movie.class);
        session.deleteAll(Actor.class);
        session.clear();
    }

    @Test
    public void testLoadMovieBuggy() throws InterruptedException {
       //Set up the Session
        getSessionFactory();
        Session session = sessionFactory.openSession();

        Movie movie = new Movie("The Matrix", 1999);

        Actor keanu = new Actor("Keanu Reeves");
        keanu.actsIn(movie);

        System.out.println("Gonna sleep...");
        Thread.sleep(30000L);

        //getSessionFactory();
        //Session session2 = sessionFactory.openSession();
        session.countEntitiesOfType(Movie.class);

        Actor carrie = new Actor("Carrie-Ann Moss");
        carrie.actsIn(movie);

        //Persist the movie. This persists the actors as well.
        session.save(movie);

        //Load a movie
        List<String> actors = new ArrayList<>();
        Movie matrix = session.load(Movie.class, movie.getId());
        for(Actor actor : matrix.getActors()) {
            actors.add(actor.getName());
        }

        assertEquals("Keanu Reeves", actors.get(0));
        assertEquals("Carrie-Ann Moss", actors.get(1));

        //close stuff
        session.clear();
    }

    @Test
    public void testLoadMovieGood() throws InterruptedException {
        //Set up the Session
        Session session = GDBSessionFactory.getInstance().getSession();

        Movie movie = new Movie("The Matrix", 1999);

        Actor keanu = new Actor("Keanu Reeves");
        keanu.actsIn(movie);

        System.out.println("Gonna sleep for 30 secs...");
        Thread.sleep(30000L);
        //SERVER IS RESTARTED

        Actor carrie = new Actor("Carrie-Ann Moss");
        carrie.actsIn(movie);

        //Persist the movie. This persists the actors as well.
        session.save(movie);

        //Load a movie
        List<String> actors = new ArrayList<>();
        Movie matrix = session.load(Movie.class, movie.getId());
        for(Actor actor : matrix.getActors()) {
            actors.add(actor.getName());
        }

        assertEquals("Keanu Reeves", actors.get(1));
        assertEquals("Carrie-Ann Moss", actors.get(0));
    }

}