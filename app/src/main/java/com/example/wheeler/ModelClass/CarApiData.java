package com.example.wheeler.ModelClass;

public class CarApiData {
    public String year;
    public String id;
    public String horsepower;
    public String make;
    public String model;
    public String price;
    public String img_url;

    public CarApiData(String year, String id, String horsepower, String make, String model, String price, String img_url) {
        this.year = year;
        this.id = id;
        this.horsepower = horsepower;
        this.make = make;
        this.model = model;
        this.price = price;
        this.img_url = img_url;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(String horsepower) {
        this.horsepower = horsepower;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public String toString() {
        return "CarApiData{" +
                "year='" + year + '\'' +
                ", id='" + id + '\'' +
                ", horsepower='" + horsepower + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", price='" + price + '\'' +
                ", image_url='" + img_url + '\'' +
                '}';
    }
}
