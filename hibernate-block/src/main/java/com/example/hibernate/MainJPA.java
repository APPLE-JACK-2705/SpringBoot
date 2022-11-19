package com.example.hibernate;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class MainJPA {
    public static void main(String[] args) {
        EntityManagerFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Catalog.class)
                .buildSessionFactory();

        EntityManager em = factory.createEntityManager();

        Catalog catalog1 = new Catalog("Comedy #1");
        Catalog catalog2 = new Catalog("Comedy #2");
        Catalog catalog3 = new Catalog("Comedy #3");
        Catalog catalog4 = new Catalog("Comedy #4");

        try{
            // CREATE
            em.getTransaction().begin();
            // Сохраняем наши объекты в записи таблицы
            em.persist(catalog1);
            em.persist(catalog2);
            em.persist(catalog3);
            em.persist(catalog4);
            em.getTransaction().commit();

            // READ
            em.getTransaction().begin();
            // Читаем наши записи в таблицы
            Catalog catalog5 = em.find(Catalog.class, 2L);
            catalog5.setTitle("New Comedy");
            System.out.println(catalog5);
            em.getTransaction().commit();
            catalog5.setTitle("Comedy #10");
            System.out.println(catalog5);

            // UPDATE
            em.getTransaction().begin();
            // Возвращаем наш объект в состояние в persist
            em.merge(catalog5);
            em.getTransaction().commit();


            // READ ALL
            em.getTransaction().begin();
            List<Catalog> list = em.createQuery("SELECT c FROM Catalog c").getResultList();
            System.out.println(list);
            em.getTransaction().commit();

            // READ by ID
            em.getTransaction().begin();
            Catalog catalog6 = em
                    .createQuery("SELECT C FROM Catalog C WHERE C.id = :id", Catalog.class)
                    .setParameter("id", 2L)
                    .getSingleResult();
            System.out.println(catalog6);
            em.getTransaction().commit();

            // NAMED QUERY(через аннотации или XML) - работает быстрее, чем JPQL
            em.getTransaction().begin();
            List<Catalog> list1 = em.createNamedQuery("Catalog.findAll", Catalog.class).getResultList();
            Catalog catalog7 = em
                    .createNamedQuery("Catalog.findById", Catalog.class)
                    .setParameter("id", 4L)
                    .getSingleResult();

            System.out.println(list1);
            System.out.println(catalog7);

            em.getTransaction().commit();
        }finally {
            em.close();
            factory.close();
        }

    }
}
