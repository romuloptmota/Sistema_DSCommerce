package com.devsuperior.dscommerce.dto;


import com.devsuperior.dscommerce.entities.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private  Double price;
    private String imgUrl;


    //SEM CONSTRUTOR SEM ARGUMENTO, NECESSARIO  @JsonCreator E @JsonProperty
    @JsonCreator
    public ProductDTO(@JsonProperty("id") Long id,
                      @JsonProperty("name") String name,
                      @JsonProperty("description") String description,
                      @JsonProperty("price") Double price,
                      @JsonProperty("imgUrl") String imgUrl) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
