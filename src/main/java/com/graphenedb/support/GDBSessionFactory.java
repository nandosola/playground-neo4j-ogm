package com.graphenedb.support;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class GDBSessionFactory {
    private static final String BOLT_URI =  "bolt://127.0.0.1:7687";
    private static final String BOLT_ENCRYPTION =  "NONE";

    private static SessionFactory sessionFactory = new SessionFactory(configuration(),
            "com.graphenedb.support.domain");
    private static GDBSessionFactory factory = new GDBSessionFactory();

    public static GDBSessionFactory getInstance() {
        return factory;
    }

    private GDBSessionFactory() {
    }

    private static org.neo4j.ogm.config.Configuration configuration() {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config.driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.bolt.driver.BoltDriver")
                .setURI(BOLT_URI)
                .setEncryptionLevel(BOLT_ENCRYPTION);
        return config;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
