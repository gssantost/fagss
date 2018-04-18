package fagss.org.srv;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import fagss.org.util.DirectoryManager;
import fagss.org.util.PropertiesMap;

/**
 * Application Lifecycle Listener implementation class FirstListener
 *
 */
@WebListener
public class FirstListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public FirstListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
		DirectoryManager.createStoragePath();
		@SuppressWarnings("unused")
		PropertiesMap prop = PropertiesMap.getInstance();
	}
	
}
