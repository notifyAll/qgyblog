package com.qiugaoyang.qgyblog.common.dao;

import com.qiugaoyang.qgyblog.common.domain.Admin;
import com.qiugaoyang.qgyblog.common.domain.AdminLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminLoginRepository extends JpaRepository<AdminLogin,Integer> {
}
