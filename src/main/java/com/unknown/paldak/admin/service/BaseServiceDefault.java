package com.unknown.paldak.admin.service;

public interface BaseServiceDefault<T> extends BaseService<T> {
	
  public void register(T t);
  
  public boolean modify(T t);
  

}
