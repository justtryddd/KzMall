package com.gyb.service.Imp;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyb.utils.PageHelper;
import com.gyb.vo.ResStatus;
import com.gyb.vo.ResultVo;
import com.gyb.entity.*;
import com.gyb.mapper.*;
import com.gyb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 2023/3/18 - 14:04
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImgMapper productImgMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductParamsMapper productParamsMapper;
    @Autowired
    private ProductCommentsMapper productCommentsMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();


    public ResultVo listRecommendProduct() {
        List<ProductVO> productVOS = productMapper.selectRecommendProduct();
        return new ResultVo(ResStatus.OK, "success", productVOS);
    }

    


    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo getProductBasicInfo(String productId) {

        Map<String, Object> basicInfo = new HashMap<>();

        String productInfo = (String) stringRedisTemplate.boundHashOps("Products").get(productId);

        //2：若存在该商品信息，获取其图片信息和套餐信息
        if (productInfo != null) {
            try {
                Product product = objectMapper.readValue(productInfo, Product.class);

                String productImgs = (String) stringRedisTemplate.boundHashOps("ProductImgs").get(productId);
                JavaType javaType1 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductImg.class);
                List<ProductImg> productImg = objectMapper.readValue(productImgs, javaType1);

                String productSkus = (String) stringRedisTemplate.boundHashOps("ProductSkus").get(productId);
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductSku.class);
                List<ProductSku> productSku = objectMapper.readValue(productSkus, javaType);

                basicInfo.put("Product", product);
                basicInfo.put("productImg", productImg);
                basicInfo.put("productSkus", productSku);
                System.out.println("--------------缓存数据库中数据");

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("----------MySQL中的数据");
            //若Redis缓存中没找到数据，进行插叙MySQL操作
            Example example = new Example(Product.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("productId", productId);
            List<Product> products = productMapper.selectByExample(example);

            if (products.size() != 0) {
                Example example1 = new Example(ProductImg.class);
                Example.Criteria criteria1 = example1.createCriteria();
                criteria1.andEqualTo("itemId", productId);
                List<ProductImg> productImgs = productImgMapper.selectByExample(example1);

                Example example2 = new Example(ProductSku.class);
                Example.Criteria criteria2 = example2.createCriteria();
                criteria2.andEqualTo("productId", productId);
                List<ProductSku> productSkus = productSkuMapper.selectByExample(example2);

                basicInfo.put("product", products.get(0));
                basicInfo.put("productImgs", productImgs);
                basicInfo.put("productSkus", productSkus);

                System.out.println("基于对MySQL进行信息查取");

                //将查询到的数据保存到Redis缓存中
                try {

                    stringRedisTemplate.boundHashOps("Products").put(productId, objectMapper.writeValueAsString(products.get(0)));
                    stringRedisTemplate.boundHashOps("ProductImgs").put(productId, objectMapper.writeValueAsString(productImgs));
                    stringRedisTemplate.boundHashOps("ProductSkus").put(productId, objectMapper.writeValueAsString(productSkus));

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }

        }
        if(basicInfo != null){
            return new ResultVo(ResStatus.OK,"success",basicInfo);
    }else
        return new ResultVo(ResStatus.NO,"erro",null);

}


    public ResultVo getProductParams(String productId) {
        Example example = new Example(ProductParams.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        List<ProductParams> productParams = productParamsMapper.selectByExample(example);

        if(productParams.size() > 0){
            return new ResultVo(ResStatus.OK,"success",productParams);
        }else {
            return new ResultVo(ResStatus.NO,"三无产品",null);
        }
    }

 /*   @Override
    public ResultVo getProductComments(String productId) {
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        List<ProductComments> productParams = productCommentsMapper.selectByExample(example);

        //如果找到商品评论，获取评论人的用户信息
        if(productParams.size() > 0){

        }

    }*/

    //分页商品评论显示业务
    public ResultVo listCommentsByProductId(String productId,int pageNum,int limit) {
//        List<ProductCommentsVo> commentsById = productCommentsMapper.getCommentsById(productId,start,limit);

        //获取PageHelper类中所需的属性
        //1.根据商品ID获取该商品的总评论数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int count = productCommentsMapper.selectCountByExample(example);

        //2.计算总页数，（必须确定每页显示多少条 pageSize=limit）
        int pageCount = count%limit==0 ? count/limit : count/limit+1;

        //3.查询当前页的数据（因评论中由用户信息，故需联表查询）
        int start = (pageNum-1)*limit;
        List<ProductCommentsVo> list = productCommentsMapper.getCommentsById(productId, start, limit);


        return new ResultVo(ResStatus.OK,"success", new PageHelper<ProductCommentsVo>(count,pageCount,list));
    }

    //查看目标商品的评论数据（好评、差评、中评、好评率）         这里注意createCriteria特点
    public ResultVo productComments(String productId) {
        //该商品的评价总数
        Example example1 = new Example(ProductComments.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("productId",productId);
        int totalCount = productCommentsMapper.selectCountByExample(example1);

        //该商品的好评数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId)
                .andEqualTo("commLevel",1);
        int goodCount = productCommentsMapper.selectCountByExample(example);

        //该商品的中评数
        Example example2 = new Example(ProductComments.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("productId",productId)
                 .andEqualTo("commLevel",0);
        int midCount = productCommentsMapper.selectCountByExample(example2);

        //该商品的差评数
        int badCount = totalCount-goodCount-midCount;

        //该商品的好评率
        double percent = (Double.parseDouble(goodCount+"") / Double.parseDouble(totalCount+"") )*100;
        //String percentValue = (percent+"").substring(0,(percent+"").lastIndexOf(".")+3);
        String percentValue = (percent+"");

        HashMap<String,Object> map = new HashMap<>();
        map.put("总评论数",totalCount);
        map.put("好评数",goodCount);
        map.put("中评数",midCount);
        map.put("差评数",badCount);
        map.put("好评率",percentValue);

        return new ResultVo(ResStatus.OK,"success",map);
    }


    public ResultVo listProductByCategoryId(int cid, int pageNum, int limit) {
        //根据前端要求：每页limit个信息，查询第pageNum页  调整下调用mapper方法
        int start = (pageNum-1) * limit;
        List<ProductVO> productVOS = productMapper.selectProductByCategoryId(cid, start, limit);


        //这里直接返回也可以，但要和前端配合，所以我们创建PageHelper使数据更明晰
        //return new ResultVo(ResStatus.OK,"success",productVOS);


        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId",cid);
        int count = productMapper.selectCountByExample(example);

        int totalPageNum = count % 10 == 0 ? count/limit : count/limit+1;

        return new ResultVo(ResStatus.OK,"success",new PageHelper<ProductVO>(count,totalPageNum,productVOS));
    }

    public ResultVo listProductBrandByCid(int cid) {

        List<String> strings = productParamsMapper.selectProductBrandsByCid(cid);

        return new ResultVo(ResStatus.OK,"success",strings);
    }


    public ResultVo listProductByProductKW(String kw, int pageNum, int limit) {

        kw = "%" + kw + "%";
        int start = (pageNum-1)*limit;
        List<ProductVO> productVOS = productMapper.selectProductByProductKw(kw, start, limit);

        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("productName",kw);

        int count = productMapper.selectCountByExample(example);

        int totalPageNum = count % 10 == 0 ? count/limit : count/limit+1;

        return new ResultVo(ResStatus.OK,"success",new PageHelper<ProductVO>(count,totalPageNum,productVOS));
    }

    public ResultVo listBrandsByProductKW(String kw) {
        kw = "%" + kw + "%";
        List<String> strings = productParamsMapper.selectProductBrandByKW(kw);
        return new ResultVo(ResStatus.OK,"success",strings);
    }
}
