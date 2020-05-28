package com.ts.gulimall.product.dao;

import com.ts.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author ts
 * @email ts@gmail.com
 * @date 2020-05-20 16:33:13
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
