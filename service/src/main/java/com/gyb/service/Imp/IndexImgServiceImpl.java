package com.gyb.service.Imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyb.mapper.IndexImgMapper;
import com.gyb.service.IndexImgService;
import com.gyb.vo.ResultVo;
import com.gyb.entity.IndexImg;
import com.gyb.vo.ResStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @date 2023/3/17 - 18:25
 */
@Service
public class IndexImgServiceImpl implements IndexImgService {

/*    @Autowired
    private IndexImgMapper indexImgMapper;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    private ObjectMapper objectMapper = new ObjectMapper();*/

    @Autowired
    private IndexImgMapper indexImgMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    private ObjectMapper objectMapper = new ObjectMapper();
    public ResultVo showIndexImg() {
        List<IndexImg> indexImgs = null;


        try {

//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            //1000个并发请求，请求轮播图
            String imgsStr = stringRedisTemplate.boundValueOps("indexImgs").get();
            //1000个请求查询到redis中的数据都是null
            if (imgsStr != null) {
                System.out.println("----------第一次查询Redis");
                // 从redis中获取到了轮播图信息
                JavaType javaType = objectMapper.getTypeFactory()
                        .constructParametricType(ArrayList.class,
                                IndexImg.class);
                indexImgs = objectMapper.readValue(imgsStr, javaType);
            } else {
                // 1000个请求都会进⼊else
                // (service类在spring容器中是单例的，
                // 1000个并发会启动1000个线程来处理，但是公⽤⼀个service实例)
                synchronized (this){
                    // 第⼆次查询redis
                    String s =
                            stringRedisTemplate.boundValueOps("indexImgs").get();
                    if(s == null){
                        // 这1000个请求中，只有第⼀个请求再次查询redis时依然为null
                        indexImgs = indexImgMapper.queryAllIndexImg();
                        System.out.println("----------------查询数据库");
                                stringRedisTemplate.boundValueOps("indexImgs")

                                        .set(objectMapper.writeValueAsString(indexImgs));
                        stringRedisTemplate.boundValueOps("indexImgs")
                                .expire(1, TimeUnit.DAYS);
                    }else{
                        System.out.println("-------------第二次查询Redis");
                        JavaType javaType =
                                objectMapper.getTypeFactory()
                                        .constructParametricType(ArrayList.class,
                                                IndexImg.class);
                        indexImgs = objectMapper.readValue(s,
                                javaType);
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //返回数据
        if(indexImgs != null){
            return new ResultVo(ResStatus.OK,"success",indexImgs);
        }else{
            return new ResultVo(ResStatus.NO,"fail",null);
        }
    }
}


























    /*    @Override
    public ResultVo showIndexImg() {

        List<IndexImg> indexImgs = new ArrayList<>();

        try {
            //首先在Redis中查询是否有轮播图图片的缓存
            String strImg = stringRedisTemplate.boundValueOps("indexImg").get();

            //如果Redis中存在，则直接返回给前端
            if (strImg != null) {
                JavaType javaType = objectMapper.getTypeFactory()
                        .constructParametricType(ArrayList.class, IndexImg.class);
                indexImgs = objectMapper.readValue(strImg, javaType);
                System.out.println("Redis缓存中的数据");
            }else {
                //Redis中没有则到数据库中查询，将查询结果放到Redis缓存中
                indexImgs = indexImgMapper.queryAllIndexImg();
                stringRedisTemplate.boundValueOps("indexImg")
                        .set(objectMapper.writeValueAsString(indexImgs));
                System.out.println("访问数据库中的数据");
            }
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        if (indexImgs.size() != 0)
            return new ResultVo(ResStatus.OK, "Success", indexImgs);
        else
            return new ResultVo(ResStatus.NO, "没有轮播图信息", null);

    }*/

