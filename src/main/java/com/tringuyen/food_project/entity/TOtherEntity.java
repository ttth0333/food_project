package com.tringuyen.food_project.entity;


import javax.persistence.*;
import java.util.Set;

@Entity(name = "t_order")
public class TOtherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "estimate_ship")
    private String estimateShip;

    @Column(name = "deliver_address")
    private String deliverAddress;

    @ManyToOne()
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @OneToMany(mappedBy = "tOther")
    private Set<OtherStatusEntity> otherStatus;

    @OneToMany(mappedBy = "tOther")
    private Set<FoodOrderEntity> foodOrders;

    public Set<FoodOrderEntity> getFoodOrders() {
        return foodOrders;
    }

    public void setFoodOrders(Set<FoodOrderEntity> foodOrders) {
        this.foodOrders = foodOrders;
    }

    public Set<OtherStatusEntity> getOtherStatus() {
        return otherStatus;
    }

    public void setOtherStatus(Set<OtherStatusEntity> otherStatus) {
        this.otherStatus = otherStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstimateShip() {
        return estimateShip;
    }

    public void setEstimateShip(String estimateShip) {
        this.estimateShip = estimateShip;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}