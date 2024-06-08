package com.unknown.paldak.admin.mapper;


import java.util.List;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.domain.CartVO;

public interface BaseMapper<T> {
    public List<T> getList();

    public void insert(T t);

    public void insertSelectKey(T t);

    public T read(Long id);

    public int delete(Long id);

    public int update(T item);

    public List<T> getListWithPaging(Criteria cri);
    
    public List<T> getDescListWithPaging(Criteria cri);
    
    public int getTotalCount(Criteria cri);
 
}
