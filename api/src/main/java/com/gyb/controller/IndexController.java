package com.gyb.controller;

import com.gyb.vo.ResultVo;
import com.gyb.service.CategoryService;
import com.gyb.service.IndexImgService;
import com.gyb.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @date 2023/3/17 - 18:19
 */

@RestController
@RequestMapping("/index")
@Api(value = "首页显示的内容",tags = "首页管理")
@CrossOrigin
public class IndexController {

     @Autowired
     private IndexImgService indexImgService;
     @Autowired
     private CategoryService categoryService;
     @Autowired
     private ProductService productService;


     @ApiOperation(value = "首页轮播图接口")
     @GetMapping("/indeximg")
     public ResultVo showIndexImg(){
          return indexImgService.showIndexImg();
     }


     @ApiOperation("商品分类查询接口")
     @GetMapping("category-list")
     public ResultVo listCategories(){
          return categoryService.listCategories();
     }

     @ApiOperation("推荐商品查询接口")
     @GetMapping("/list-recommends")
     public ResultVo listRecommendProduct(){
          return productService.listRecommendProduct();
     }


     @ApiOperation("分级商品推荐接口")
     @GetMapping("/category-recommends")
     public ResultVo listFirstProduct(){
          return categoryService.listFirstLevelCategories();
     }

}
