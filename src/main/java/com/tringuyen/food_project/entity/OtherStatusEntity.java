package com.tringuyen.food_project.entity;

import com.tringuyen.food_project.entity.id.OtherStatusId;

import javax.persistence.*;

@Entity(name = "order_status")
@IdClass(OtherStatusId.class)
public class OtherStatusEntity {
    @Id
    private int id_order;

    @Id
    private int id_status;

    @ManyToOne()
    @JoinColumn(name = "id_order", insertable = false, updatable = false)
    private TOtherEntity tOther;

    @ManyToOne()
    @JoinColumn(name = "id_status", insertable = false, updatable = false)
    private StatusEntity status;

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public TOtherEntity gettOther() {
        return tOther;
    }

    public void settOther(TOtherEntity tOther) {
        this.tOther = tOther;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }
}