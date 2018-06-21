package com.qiugaoyang.qgyblog.common.dao;

import com.qiugaoyang.qgyblog.common.domain.AllClass;
import com.qiugaoyang.qgyblog.common.domain.ClassMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllClassRepository extends JpaRepository<AllClass,Integer>{

    List<AllClass> findAllByClassMappingAndAllClassState(ClassMapping classMapping,Integer allClassState);
}
