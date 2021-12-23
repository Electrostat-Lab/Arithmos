package sorting;

// add some recursive loops for merge sort and mirror sort algos
public final class SortUtils {

    private static SortUtils utils;
    
    public enum Directionality {
        FORWARD(0), BACKWARD(1);
        public final int ID;
        Directionality(final int ID) {
            this.ID = ID;
        } 
    }
    
    private SortUtils() {
    }
    
    public static SortUtils getInstance() {
        if(utils == null) {
            synchronized(utils) {
                if(utils == null) {   
                    return new SortUtils();
                }
            }
        }
        return null;
    }
    
    public <T> void recursiveLoop(final T[] array, int startIndex, final Directionality direction, final ActionInjection injector) {
        switch(direction.ID) {
            case 1:
                if(startIndex > 0) {
                // call user code
                     if(injector != null) {
                        injector.inject();
                     }
                // advance and recall
                --startIndex;
                recursiveLoop(array, startIndex, Directionality.BACKWARD, injector);
              }
              return;
            default:
                 if(startIndex < array.length) {
                    // call user code
                    if(injector != null) {
                        injector.inject();
                    }
                    // advance and recall
                    ++startIndex;
                    recursiveLoop(array, startIndex, Directionality.FORWARD, injector);
                }
        }
    }
     
     public <T> void customComparative(int startIndex, int endIndex, final Directionality direction, final ActionInjection injector) {
       switch(direction.ID) {
           case 1:
                // backward motion until reaching the startIndex.
                if(startIndex > endIndex) {
                    // call user code
                    if(injector != null) {
                        injector.inject();
                    }
                    // advance and recall
                    startIndex -= endIndex;
                    customComparative(startIndex, endIndex, direction, injector);
                }
              return;
           default:
                // forward motion until reaching the endIndex.
                if(startIndex < endIndex) {
                    // call user code
                    if(injector != null) {
                        injector.inject();
                    }
                    // advance and recall
                    startIndex += endIndex;
                    customComparative(startIndex, endIndex, direction, injector);
                }
       }
      
     }
    
    public interface ActionInjection {
        void inject();
    }
}