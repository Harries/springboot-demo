package com.et.olingo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CHILD")
public class Child {
    @EmbeddedId
    private ChildPK childPK = new ChildPK();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("fatherId")
    private Father father;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("motherId")
    private Mother mother;

    private String name;

    private String surname;

    public Child() {
    }

    public Child(Father father, Mother mother) {
        this.childPK = new ChildPK(father, mother);
    }

    public ChildPK getChildPK() {
        return childPK;
    }

    public void setChildPK(ChildPK childPK) {
        this.childPK = childPK;
    }

    public Father getFather() {
        return father;
    }

    public void setFather(Father father) {
        this.father = father;
    }

    public Mother getMother() {
        return mother;
    }

    public void setMother(Mother mother) {
        this.mother = mother;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child child = (Child) o;
        return Objects.equals(childPK, child.childPK) &&
                Objects.equals(father, child.father) &&
                Objects.equals(mother, child.mother) &&
                Objects.equals(name, child.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(childPK, father, mother, name);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
