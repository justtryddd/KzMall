package com.gyb.controller;

import com.gyb.service.ProductService;
import com.gyb.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @date 2023/3/19 - 13:30
 */

@RestController
@CrossOrigin
@RequestMapping("/product")
@Api(value = "商品信息相关接口",tags = "商品管理")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("商品基本信息查询接口")
    @GetMapping("/detail-info/{pid}")
    public ResultVo getProductBasicInfo(@PathVariable("pid")String pid){
        return productService.getProductBasicInfo(pid);
    }


    @ApiOperation("商品参数信息查询接口")
    @GetMapping("/detail-params/{pid}")
    public ResultVo getProductParams(@PathVariable("pid")String pid){
        return productService.getProductParams(pid);
    }

    @ApiOperation("商品评论信息查询接口")
    @GetMapping("/detail-commonts/{pid}")
    public ResultVo getProductComments(@PathVariable("pid")String pid,int pageNum,int limit){
        return productService.listCommentsByProductId(pid,pageNum,limit);
    }

    @ApiOperation("商品评论统计接口")
    @GetMapping("/detail-commontscount/{pid}")
    public ResultVo getProductComments(@PathVariable("pid")String pid){
        return productService.productComments(pid);
    }

    @ApiOperation("根据商品类别查询商品信息")
    @GetMapping("/listbycid/{cid}")
    public ResultVo listProductByCategoryId(@PathVariable("cid")int cid,int pageNum,int limit){
        ResultVo resultVo = productService.listProductByCategoryId(cid, pageNum, limit);
        return resultVo;
    }


    @ApiOperation("根据商品类别查询商品品牌")
    @GetMapping("/listbrands/{cid}")
    public ResultVo getProductBrandByCategoryId(@PathVariable("cid") int cid) {
        ResultVo resultVo = productService.listProductBrandByCid(cid);
        return resultVo;
    }


    @ApiOperation("根据商品关键字查询商品信息")
    @GetMapping("/listbykeyword/{kw}")
    public ResultVo searchProducts(@PathVariable("kw")String kw,int pageNum,int limit){
        ResultVo resultVo = productService.listProductByProductKW(kw, pageNum, limit);
        return resultVo;
    }


    @ApiOperation("根据商品关键字查询商品品牌")
    @GetMapping("//listbrands-keyword")
    public ResultVo searchProductbrands(String kw) {
        ResultVo resultVo = productService.listBrandsByProductKW(kw);
        return resultVo;
    }
}
