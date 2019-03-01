package com.wl.base.dao;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * @author WL
 * @date 2019/2/28
 * @param <T>
 */
public abstract class AbstractGenericDao<T> extends SqlSessionDaoSupport implements ApplicationContextAware {
    protected Class<T> clazz;

    protected ApplicationContext context;

    protected ApplicationContext getContext() {
        return context;
    }
    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        this.context = context;
    }

    /**
     *通过范型反射，取得在子类中定义的class.
     * @return class
     */
    protected String getNameSpace() {
        clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz.getSimpleName();
    }
    /**
     * 切换数据源
     * @param name
     * 传递sqlMapClient或sqlMapClient2
     * @see
     */
    public void choseSqlClient(String name) {
        SqlSessionFactory client = (SqlSessionFactory) getContext().getBean(name);
        setSqlSessionTemplate(new SqlSessionTemplate(client));
        afterPropertiesSet();
    }

    /**
     * 保存数据
     * @param entity
     * @return
     */
    public T save(T entity) {
        getSqlSession().insert(getNameSpace() + ".save", entity);
        return entity;
    }

    /**
     * 更新对象
     * @param entity
     * @return
     */
    public T update(T entity) {
        getSqlSession().update(getNameSpace() + ".update", entity);
        return entity;
    }

    /**
     * 删除数据
     * @param entity
     * @return
     */
    public boolean delete(T entity){
        return (getSqlSession().delete(getNameSpace() + ".delete", entity)>0)?true:false;
    }
    /**
     *map查询.包含各种属性的查询
     * @param params
     * @param pageParams
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List<T> find(Map params, int... pageParams) {
        if ((pageParams != null) && (pageParams.length > 0)) {
            int rowStart = 0;
            int pageSize = 0;
            rowStart = Math.max(0, pageParams[0]);
            if (pageParams.length > 1) {
                pageSize = Math.max(0, pageParams[1]);
            }
            return this.findForPage(params, rowStart, pageSize);
        } else {
            return this.getSqlSession().selectList(
                    getNameSpace() + ".find",params);
        }

    }

    /**
     * 分页查询函数，使用List.
     * @param params pageNo页号从1开始.
     * @param rowStart
     * @param pageSize
     * @return含总记录数和当前页数据的Page对象.
     */
    @SuppressWarnings("rawtypes")
    private List findForPage(Map params, int rowStart, int pageSize) {
        Assert.isTrue(rowStart >= 0, "rowStart should start from 0");
        RowBounds rowBound = new RowBounds(rowStart, pageSize);
        List list = getSqlSession().selectList(
                getNameSpace() + ".find",params,rowBound);
        return list;

    }

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public T get(Serializable id) {
        T entity = (T) getSqlSession().selectOne(
                getNameSpace() + ".get", id);
        return entity;
    }


    /**
     * 查询总数
     * @param params
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Long getTotalCount(Map params) {
        Long count = (Long)getSqlSession().selectOne(getNameSpace() + ".totalCount", params);
        return (count == null ? 0 : count);
    }


    /**
     * 用于mysql分页查询
     * @param params
     * @param pageParams
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public List<T> findPaging(Map params, int... pageParams) {
        if ((pageParams != null) && (pageParams.length > 0)) { // 如果页面参数为空
            int rowStart = 0;
            int pageSize = 0;
            rowStart = Math.max(0, pageParams[0]);
            if (pageParams.length > 1) {
                pageSize = Math.max(0, pageParams[1]);
            }
            params.put("rowStart", rowStart);
            params.put("pageSize", pageSize);
            List list = getSqlSession().selectList(getNameSpace() + ".find", params);
            return list;
        } else {
            return this.getSqlSession().selectList(getNameSpace() + ".find", params);

        }

    }
}
