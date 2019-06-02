package com.dem.es.entity.po;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Transactions implements Serializable {

    /**
     * price : 30000
     * color : green
     * make : ford
     * sold : 2014-05-18
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
