package com.ts.gulimall.coupon.dao;

import com.ts.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author ts
 * @email ts@gmail.com
 * @date 2020-05-24 09:44:31
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
