package com.example.sidharth.artoonotes;

public class Product {
    private int _id;
    private String _productname;
    private String creation_Time;
    byte[] _image;
    public Product(){
        this._productname=" ";
        this.creation_Time= " ";
    }
    public Product(String productname)
    {
        this._productname = productname;
    }
    public Product(String productname,byte[] image){
        this._productname = productname;
        this._image = image;
    }
    public void setCreationTime(String creation_Time){
        this.creation_Time = creation_Time;
    }
    public String getCreationTime()
    {
        return creation_Time;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public byte[] getImage() {
        return this._image;
    }
    public void set_productname(String _productname) {
        this._productname = _productname;
    }
    public int get_id() {
        return _id;
    }
    public String get_productname() {
        return _productname;
    }
    public void setImage(byte[] image) {
        this._image = image;
    }
}