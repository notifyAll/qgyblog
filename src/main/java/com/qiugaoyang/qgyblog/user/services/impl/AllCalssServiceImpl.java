package com.qiugaoyang.qgyblog.user.services.impl;

import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.dao.AllClassRepository;
import com.qiugaoyang.qgyblog.common.dao.ClassMappingRepository;
import com.qiugaoyang.qgyblog.common.domain.AllClass;
import com.qiugaoyang.qgyblog.common.domain.ClassMapping;
import com.qiugaoyang.qgyblog.user.services.AllCalssService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AllCalssServiceImpl implements AllCalssService{

    @Resource
    AllClassRepository allClassRepository;
    @Resource
    ClassMappingRepository classMappingRepository;
    /**
     * 查询所有可用的类目
     *
     * @return 可用的类目
     */
    @Override
    public List<AllClass> findAllBlogClass() {
        ClassMapping byClassMappingName = classMappingRepository.findByClassMappingName(Params.CLASS_MAPPERING_BLOG);
        List<AllClass> list=allClassRepository.findAllByClassMappingAndAllClassState(byClassMappingName,1);
        return list;
    }
}
