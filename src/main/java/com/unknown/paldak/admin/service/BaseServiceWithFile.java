package com.unknown.paldak.admin.service;


import org.springframework.web.multipart.MultipartFile;

public interface BaseServiceWithFile<T> extends BaseService<T>{
  
  public boolean modify(MultipartFile[] uploadFile, T t);
  
  public void register(MultipartFile[] uploadFile, T t);
  
}
