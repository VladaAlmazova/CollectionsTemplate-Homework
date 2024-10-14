package ru.naumen.collection.task1;

/**
 * Билет
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Ticket {
    private long id;
    private String client;

    //переопределение hashCode и equals,
    // чтобы экземляры класса Ticket можно было хранить по хэшу
    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;
        if(obj instanceof Ticket){
            Ticket that = (Ticket) obj;
            return that.id == this.id;
        }
        return false;
    }
}
