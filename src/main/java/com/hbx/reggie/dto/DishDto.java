package com.hbx.reggie.dto;


import com.hbx.reggie.dao.Dish;
import com.hbx.reggie.dao.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
