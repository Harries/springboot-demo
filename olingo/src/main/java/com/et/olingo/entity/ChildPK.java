package com.et.olingo.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ChildPK implements Serializable {

    @Column(name = "FATHER_ID", nullable = false)
    private Long fatherId;

    @Column(name = "MOTHER_ID", nullable = false)
    private Long motherId;

    public ChildPK() {
    }

    public ChildPK(Father father, Mother mother) {
        this.fatherId = father.getId();
        this.motherId = mother.getId();
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    public Long getMotherId() {
        return motherId;
    }

    public void setMotherId(Long motherId) {
        this.motherId = motherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildPK childPK = (ChildPK) o;
        return Objects.equals(fatherId, childPK.fatherId) &&
                Objects.equals(motherId, childPK.motherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fatherId, motherId);
    }
}