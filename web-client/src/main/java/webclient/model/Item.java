package webclient.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private boolean bought;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return name;
    }

//    @ManyToOne
//    private ShoppingList shoppingList;
}
