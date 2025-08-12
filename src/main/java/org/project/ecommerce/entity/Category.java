package org.project.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Category_Id")
    private int id;
    private String name;
    public Category(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /*
    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
     */
}
