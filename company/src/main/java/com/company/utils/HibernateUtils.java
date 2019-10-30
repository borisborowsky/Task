package com.company.utils;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import com.company.catalogue.Book;
import com.company.catalogue.BookUnit;
import com.company.catalogue.Fine;
import com.company.users.Employee;
import com.company.users.Member;
import com.company.users.Person;

public class HibernateUtils { 

	   private static StandardServiceRegistry registry;
	   private static SessionFactory sessionFactory;

	   public static SessionFactory getSessionFactory() {
	      if (sessionFactory == null) {
	         try {
	            StandardServiceRegistryBuilder registryBuilder = 
	                  new StandardServiceRegistryBuilder();

	            Map<String, Object> settings = new HashMap<>();
	            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
	            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/library7");
	            settings.put(Environment.USER, "root");
	            settings.put(Environment.PASS, "280486");
	            settings.put(Environment.HBM2DDL_AUTO, "update");
	            settings.put(Environment.SHOW_SQL, true);
	            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
	            
	            // Hibernate search properties
	            settings.put("hibernate.search.default.directory_provider", "filesystem");
	            settings.put("hibernate.search.default.indexBase", "C:/hibernate/lucence/indexes7");
	            settings.put("hibernate.search.default.indexwriter.infostream", true);

	            registryBuilder.applySettings(settings);

	            registry = registryBuilder.build();
	            MetadataSources sources = new MetadataSources(registry)
	            		.addAnnotatedClass(Book.class)
						.addAnnotatedClass(BookUnit.class).addAnnotatedClass(Member.class)
						.addAnnotatedClass(Employee.class).addAnnotatedClass(Person.class)
						.addAnnotatedClass(Fine.class);

	            Metadata metadata = sources.getMetadataBuilder().build();
	            sessionFactory = metadata.getSessionFactoryBuilder().build();
	         } catch (Exception e) {
	            if (registry != null) {
	               StandardServiceRegistryBuilder.destroy(registry);
	            }
	            e.printStackTrace();
	         }
	      }
	      return sessionFactory;
	   }

	   public static void shutdown() {
	      if (registry != null) {
	         StandardServiceRegistryBuilder.destroy(registry);
	      }
	   }
	}