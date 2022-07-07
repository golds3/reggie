package com.hbx.reggie.dto;


import com.hbx.reggie.dao.Setmeal;
import com.hbx.reggie.dao.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
