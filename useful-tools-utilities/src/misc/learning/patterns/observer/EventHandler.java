package misc.learning.patterns.observer;

import java.util.Observable;
import java.util.Observer;  /* this is Event Handler */
 
public class EventHandler implements Observer {
    private String response;
    public void update (Observable obj, Object arg) {
        if (arg instanceof String) {
            response = (String) arg;
            System.out.println("Received Response in Event Handler: "+ response );
        }
    }
}