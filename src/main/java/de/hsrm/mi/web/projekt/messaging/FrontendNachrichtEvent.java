package de.hsrm.mi.web.projekt.messaging;
/**
 *  die Informationen zu einer Änderung an den Touren in einer strukturierten Form zu kapseln
 */
public class FrontendNachrichtEvent {
    public enum EventType {
        TOUR
    }

    public enum Operation {
        CREATE, UPDATE, DELETE
    }
    private EventType type;
    private long id;
    private Operation operation;
     // Konstruktoren
     public FrontendNachrichtEvent() {}

     public FrontendNachrichtEvent(EventType type, long id, Operation operation) {
         this.type = type;
         this.id = id;
         this.operation = operation;
     }
 
     // Getter und Setter
     public EventType getType() {
         return type;
     }
 
     public void setType(EventType type) {
         this.type = type;
     }
 
     public long getId() {
         return id;
     }
 
     public void setId(long id) {
         this.id = id;
     }
 
     public Operation getOperation() {
         return operation;
     }
 
     public void setOperation(Operation operation) {
         this.operation = operation;
     }
 
}
