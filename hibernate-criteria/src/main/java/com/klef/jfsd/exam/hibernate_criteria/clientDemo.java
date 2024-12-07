package com.klef.jfsd.exam.hibernate_criteria;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        // Create session factory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Insert records
        insertRecords(sessionFactory);

        // Apply Criteria queries
        applyCriteriaQueries(sessionFactory);

        sessionFactory.close();
    }

    private static void insertRecords(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Customer c1 = new Customer();
        c1.setName("Alice");
        c1.setEmail("alice@example.com");
        c1.setAge(25);
        c1.setLocation("New York");

        Customer c2 = new Customer();
        c2.setName("Bob");
        c2.setEmail("bob@example.com");
        c2.setAge(30);
        c2.setLocation("San Francisco");

        session.save(c1);
        session.save(c2);

        tx.commit();
        session.close();
    }

    private static void applyCriteriaQueries(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();

        // Example: Get customers with age greater than 25
        Criteria criteria1 = session.createCriteria(Customer.class);
        criteria1.add(Restrictions.gt("age", 25));
        List<Customer> result1 = criteria1.list();
        System.out.println("Customers with age > 25:");
        result1.forEach(c -> System.out.println(c.getName()));

        // Example: Get customers with name like 'A%'
        Criteria criteria2 = session.createCriteria(Customer.class);
        criteria2.add(Restrictions.like("name", "A%"));
        List<Customer> result2 = criteria2.list();
        System.out.println("Customers with name starting with 'A':");
        result2.forEach(c -> System.out.println(c.getName()));

        session.close();
    }
}

