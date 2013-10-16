package misc.learning.patterns.observer;


//Simple example of using 'java.util.Observable' Observer pattern
//http://en.wikipedia.org/wiki/Observer_pattern

public class ObserverPatternImpTest {
    public static void main(String args[]) {
        System.out.println("Enter Text to trigger an event >");
 
        // create an event source - reads from stdin
        final EventSource evSrc = new EventSource();
 
        // create an observer
        final EventHandler respHandler = new EventHandler();
 
        // subscribe the observer to the event source
        evSrc.addObserver( respHandler );
 
        // starts the event thread
        Thread thread = new Thread(evSrc);
        thread.start();
    }
}