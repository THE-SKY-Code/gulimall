package com.ts.gulimall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;

import com.ts.gulimall.product.dao.CategoryDao;
import com.ts.gulimall.product.entity.CategoryEntity;
import com.ts.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
   private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List listWithTree() {
        //查出所有分类
        List<CategoryEntity> categoryEntities = categoryDao.selectList(null);
        //一级分类
        List<CategoryEntity> levelMmenu1 = categoryEntities.stream().filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map((menu)->{
                    menu.setChildren(getChildrens(menu,categoryEntities));
                    return menu;
                })
                .sorted((menu1,menu2)->{
                    return (menu1.getSort()==null?0:menu1.getSort() )-  (menu2.getSort()==null?0:menu2.getSort() );
                })
                .collect(Collectors.toList());

                return  levelMmenu1;
    }

    @Override
    public void removeMenu(Long[] catIds) {
        List list =  Arrays.asList(catIds);
        //TODO 检查当前菜单是否被引用
        categoryDao.deleteBatchIds(list);
    }

    //递归查找子菜单
    private  List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> childrens = all.stream().filter(categoryEntity->{
            return  categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity->{
            categoryEntity.setChildren(getChildrens(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort() )- (menu2.getSort()==null?0:menu2.getSort() );
        }).collect(Collectors.toList());
        return childrens;
    }

}