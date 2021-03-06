
package slitclient;

import db.DBDeleterRemote;
import db.DBInserterRemote;
import db.DBQuerierRemote;
import db.DBUpdaterRemote;
import db.DBUtilRemote;
import db.dbConnectorRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

/**
 * THIS CLASS IS USING A NOT THREAD SAFE SINGLETON PATTERN.
 *
 * The purpose of this class is to connect to the EJB dbConnectorRemote, and provide 
 * this connection to other classes thats needs it by using a simple getMethod.
 *  
 * This way we will only need to change the address one place if we ever need
 * to change the lookup address.
 *
 * TO USE THIS CLASS WRITE:
 * EJBConnector ejbConnector = EJBConnector.getInstance();
 *
 * @author Viktor Setervang
 * @author Yngve Ranestad
 */
public class EJBConnector {
    private static dbConnectorRemote ejbRemote;
    private static DBInserterRemote dbInserter;
    private static DBUpdaterRemote dbUpdater;
    private static DBDeleterRemote dbDeleter;
    private static DBQuerierRemote dbQuerier;
    private static DBUtilRemote dbUtil;
    
    // creates the only instance of EJBConnector.
    private static final EJBConnector INSTANCE = new EJBConnector();
    
    /**
     * The constructor is private so no class other then itself can call it.
     */
    private EJBConnector() {
        System.out.println("Starting...");
        System.out.println("Establishing connection to the bean..");
        InitialContext initialContext;        
        try {
            initialContext = new InitialContext();
            ejbRemote = (dbConnectorRemote) initialContext.lookup("java:global/SLIT/SLIT-ejb/dbConnector"); 
            dbInserter = (DBInserterRemote) initialContext.lookup("java:global/SLIT/SLIT-ejb/DBInserter"); 
            dbUpdater = (DBUpdaterRemote) initialContext.lookup("java:global/SLIT/SLIT-ejb/DBUpdater"); 
            dbDeleter = (DBDeleterRemote) initialContext.lookup("java:global/SLIT/SLIT-ejb/DBDeleter");
            dbQuerier = (DBQuerierRemote) initialContext.lookup("java:global/SLIT/SLIT-ejb/DBQuerier"); 
            dbUtil = (DBUtilRemote) initialContext.lookup("java:global/SLIT/SLIT-ejb/DBUtil"); 
        } catch (NamingException ex) {
            Logger.getLogger(EJBConnector.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Serveren er nede, vennligst prøv igjen seinere"); 
            System.exit(1);
        }    
    }
    

    /**
     * Gets the single instance of the EJBConnector.
     * Its static so no different variations of it exist.
     * 
     * @return the instance of the CommandList
     */
    public static EJBConnector getInstance() {
        return INSTANCE;
    }
    
    /**
     * Use this method to get the enterprise java bean connection
     * 
     * @return 
     */
    public dbConnectorRemote getEjbRemote() {
        return ejbRemote;
    }
    
    /**
     * Use this method to get the EJB handling database insertion.
     * 
     * @return 
     */
    public DBInserterRemote getDBInserter() {
        return dbInserter;
    }
    
    /**
     * Use this method to get the EJB that handles updating database.
     * 
     * @return 
     */
    public DBUpdaterRemote getDBUpdater() {
        return dbUpdater;
    }
    
    /**
     * Use this method to get the EJB that handles deletion from database.
     * 
     * @return 
     */
    public DBDeleterRemote getDBDeleter() {
        return dbDeleter;
    }
    
    /**
     * Use this method to get the EJB that handles database queries.
     * 
     * @return 
     */
    public DBQuerierRemote getDBQuerier() {
        return dbQuerier;
    }

    /**
     * Use this method to get the EJB that contains utility methods.
     * 
     * @return 
     */
    public DBUtilRemote getDBUtil() {
        return dbUtil;
    }

}
