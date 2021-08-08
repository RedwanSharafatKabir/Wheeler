package com.example.wheeler.ModelClass;

public class StoreOrderList {

    private String quantity;
    private String cost;
    private String city;
    private String area;
    private String road;
    private String house;
    private String carImageUrl;
    private String carBrand;
    private String carModel;
    private String carHorsepower;
    private String singlePrice;

    public StoreOrderList() {
    }

    public StoreOrderList(String quantity, String cost, String city, String area, String road, String house,
                          String carImageUrl, String carBrand, String carModel, String carHorsepower, String singlePrice) {

        this.quantity = quantity;
        this.cost = cost;
        this.city = city;
        this.area = area;
        this.road = road;
        this.house = house;
        this.carImageUrl = carImageUrl;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carHorsepower = carHorsepower;
        this.singlePrice = singlePrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCarImageUrl() {
        return carImageUrl;
    }

    public void setCarImageUrl(String carImageUrl) {
        this.carImageUrl = carImageUrl;
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

    public String getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(String singlePrice) {
        this.singlePrice = singlePrice;
    }
}
