package com.chojnacki.shoppinglist.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private Date creationDate;
    private Date recentUpdate;

    @ManyToOne
    private User owner;

    @OneToMany
    @JoinColumn(name = "shopping_list_id")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Item> items;

    @PrePersist
    private void creationDate(){
        creationDate = new Date();
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getRecentUpdate() {
        return recentUpdate;
    }

    public void setRecentUpdate(Date recentUpdate) {
        this.recentUpdate = recentUpdate;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingList that = (ShoppingList) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return  name;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
