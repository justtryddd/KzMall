package com.gyb.mapper;

import com.gyb.entity.IndexImg;
import com.gyb.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IndexImgMapper extends GeneralDao<IndexImg> {

    public List<IndexImg> queryAllIndexImg();

}