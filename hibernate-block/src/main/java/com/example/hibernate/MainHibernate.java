package com.example.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class MainHibernate {
    public static void main(String[] args) {
        // Создаем наши каталоги
        Catalog catalog1 = new Catalog("Star wars #1");
        Catalog catalog2 = new Catalog("Star wars #2");
        Catalog catalog3 = new Catalog("Star wars #3");
        Catalog catalog4 = new Catalog("Star wars #4");

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Catalog.class)
                .buildSessionFactory();

        //CRUD
        Session session = null;

        try{
            //CREATE
            // Получаем текущую сессию
            session = factory.getCurrentSession();
            // Начинаем транзакцию и добавляем в таблицу объекты каталогов
            session.beginTransaction();
            session.save(catalog1);
            session.save(catalog2);
            session.save(catalog3);
            session.save(catalog4);
            // Получаем нашу транзакцию и сохраняем их в таблицу
            session.getTransaction().commit();

            //READ
            session = factory.getCurrentSession();
            session.beginTransaction();
            // Получаем нашу запись с таблицы по id = 3
            Catalog catalog5 = session.get(Catalog.class, 3L);
            // Сохраянем нашу транзакцию
            session.getTransaction().commit();
            System.out.println(catalog5);

            //UPDATE
            session = factory.getCurrentSession();
            session.beginTransaction();
            Catalog catalog6 = session.get(Catalog.class, 2L);
            // Метод позволяющий не наблюдать Hibernate за обьектом
            //session.detach(catalog6);
            // Обновляем наши данные у обьекта
            catalog6.setTitle("Star wars #10");
            session.getTransaction().commit();
            System.out.println(catalog6);

            // DELETE
            session = factory.getCurrentSession();
            session.beginTransaction();
            Catalog catalog7 = session.get(Catalog.class, 3L);
            Catalog catalog8 = new Catalog("Star wars #5");
            // Добавление записи в таблицу через метод JPA
            session.persist(catalog8);
            session.delete(catalog7);
            session.getTransaction().commit();

            // READ ALL
            session = factory.getCurrentSession();
            session.beginTransaction();
            List<Catalog> listCatalog = session
                    .createQuery("from Catalog").getResultList();
            System.out.println(listCatalog);
            session.getTransaction().commit();

            //READ + CONDITION
            session = factory.getCurrentSession();
            session.beginTransaction();
            List<Catalog> catalogList = session
                    .createQuery("from Catalog C where C.title = :t")
                    .setParameter("t", "Star wars #5")
                    .getResultList();
            // from Catalog C where C.title = 'Star wars #1' or c.id = 3
            // from Catalog C where C.title LIKE 'Star%'
            System.out.println(catalogList);
            session.getTransaction().commit();

            //UPDATE/DELETE + CONDITION
            session = factory.getCurrentSession();
            session.beginTransaction();
            // UPDATE + CONDITION
            session
                    .createQuery("update Catalog C set title = 'Final Fantasy #4' where C.id = 1")
                    .executeUpdate();
            session
                    .createQuery("delete Catalog where id = 2")
                    .executeUpdate();
            session.getTransaction().commit();
        }finally {
            session.close();
            factory.close();
        }
    }
}