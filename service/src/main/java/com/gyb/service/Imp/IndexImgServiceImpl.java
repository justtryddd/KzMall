package com.gyb.service.Imp;

import com.gyb.entity.IndexImg;
import com.gyb.mapper.IndexImgMapper;
import com.gyb.service.IndexImgService;
import com.gyb.vo.ResStatus;
import com.gyb.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @date 2023/3/17 - 18:25
 */
@Service
public class IndexImgServiceImpl implements IndexImgService {

    @Autowired
    private IndexImgMapper indexImgMapper;

    @Override
    public ResultVo showIndexImg() {

        List<IndexImg> indexImgs = indexImgMapper.queryAllIndexImg();

        if(indexImgs.size() != 0)
            return new ResultVo(ResStatus.OK,"Success",indexImgs);
        else
            return  new ResultVo(ResStatus.NO,"index not enough",null);
    }
}
