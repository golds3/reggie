package com.hbx.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbx.reggie.common.R;
import com.hbx.reggie.dao.Category;
import com.hbx.reggie.service.CateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CateController {
    @Autowired
    private CateService cateService;
    @PostMapping
    public R<String> addCategory(@RequestBody Category category){
        log.info("开始工作");
        cateService.save(category);
        return R.success("添加成功");
    }
    @GetMapping("/page")
    public R<Page> listCategory(int page,int pageSize){
        Page<Category> categoryPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.orderByAsc(Category::getSort);
        cateService.page(categoryPage,categoryLambdaQueryWrapper);
        return R.success(categoryPage);
    }

    @DeleteMapping
    public R<String> deleCategory(Long id){
        cateService.remove(id);
        return R.success("删除成功");
    }

    @PutMapping
    public R<String> updateCategory(@RequestBody Category category){
        cateService.updateById(category);
        return R.success("修改成功");
    }
    @GetMapping("/list")
    public R<List<Category>> listCategory(Category category){
        log.info("菜品分类展示");
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.eq(Category::getType,category.getType());
        categoryLambdaQueryWrapper.orderByDesc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = cateService.list(categoryLambdaQueryWrapper);
        return R.success(list);
    }






}
