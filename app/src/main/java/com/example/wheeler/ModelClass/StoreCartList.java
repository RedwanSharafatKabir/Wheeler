package com.example.wheeler.ModelClass;

public class StoreCartList {
    private String carId;
    private String carBrand;
    private String carModel;
    private String carHorsepower;
    private int quantity;
    private String carFinalPrice;

    public StoreCartList() {
    }

    public StoreCartList(String carId, String carBrand, String carModel, String carHorsepower, int quantity, String carFinalPrice) {
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carHorsepower = carHorsepower;
        this.quantity = quantity;
        this.carFinalPrice = carFinalPrice;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarHorsepower() {
        return carHorsepower;
    }

    public void setCarHorsepower(String carHorsepower) {
        this.carHorsepower = carHorsepower;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCarFinalPrice() {
        return carFinalPrice;
    }

    public void setCarFinalPrice(String carFinalPrice) {
        this.carFinalPrice = carFinalPrice;
    }
}
