package com.unknown.paldak.admin.service;

import java.util.List;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;

public interface BaseService<T> {
  public void register(T t);
  
  public T get(Long id);
  
  public boolean modify(T t);
  
  public boolean remove(Long id);
  
  public List<T> getList(Criteria cri);
  
//  public List<T> getDescList(Criteria cri);
  
  public int getTotal(Criteria cri);
  
  public PageDTO getPageMaker(Criteria cri);
}
