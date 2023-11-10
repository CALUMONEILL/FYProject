package com.example.connectdbattempt1;

public class RatingDataModel {
    private int id;
    private String rating;

    public RatingDataModel(int id, String rating) {
        this.id = id;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
