package com.dem.es.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
public class Transactions implements Serializable {

    /**
     * price : 30000
     * color : green
     * make : ford
     * sold : 2014-05-18
     */
    @GeneratedValue
    @Id
    private Long id;
    @SerializedName("price")
    private double price;
    @SerializedName("color")
    private String color;
    @SerializedName("make")
    private String make;
    @SerializedName("sold")
    private Date sold;


}
