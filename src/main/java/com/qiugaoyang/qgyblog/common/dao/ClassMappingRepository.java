package com.qiugaoyang.qgyblog.common.dao;

import com.qiugaoyang.qgyblog.common.domain.AllClass;
import com.qiugaoyang.qgyblog.common.domain.ClassMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassMappingRepository extends JpaRepository<ClassMapping,Integer>{

    ClassMapping findByClassMappingName(String classMappingName);
}
