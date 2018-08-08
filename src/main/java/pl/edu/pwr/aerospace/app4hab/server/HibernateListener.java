package pl.edu.pwr.aerospace.app4hab.server;

import pl.edu.pwr.aerospace.app4hab.server.daos.Db;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateListener implements ServletContextListener {

    /**
     * Sets up database connection after webapp deployment
     * @param event
     */
    public void contextInitialized(ServletContextEvent event) {
        Db.getSessionFactory();
    }

    /**
     * Stops database connection after webapp undeployment
     * @param event
     */
    public void contextDestroyed(ServletContextEvent event) {
        Db.getSessionFactory().close();
    }
}