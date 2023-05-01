package com.github.vaatech.aom.config.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PageResource<T> implements Page<T> {
  private Page<T> page;

  public PageResource(Page<T> page) {
    this.page = page;
  }

  public PageResource() {}

  @Override
  @JsonIgnore
  public Pageable getPageable() {
    return this.page.getPageable();
  }

  @Override
  @JsonIgnore
  public int getTotalPages() {
    return page.getTotalPages();
  }

  @Override
  public long getTotalElements() {
    return page.getTotalElements();
  }

  @Override
  public int getNumber() {
    return page.getNumber();
  }

  @Override
  public int getSize() {
    return page.getSize();
  }

  @Override
  public int getNumberOfElements() {
    return page.getNumberOfElements();
  }

  @Override
  public List<T> getContent() {
    return page.getContent();
  }

  @Override
  public boolean hasContent() {
    return page.hasContent();
  }

  @Override
  public Sort getSort() {
    return page.getSort();
  }

  @Override
  @JsonIgnore
  public boolean isFirst() {
    return false;
  }

  @Override
  @JsonIgnore
  public boolean isLast() {
    return false;
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public boolean hasPrevious() {
    return false;
  }

  @Override
  @JsonIgnore
  public Pageable nextPageable() {
    return null;
  }

  @Override
  @JsonIgnore
  public Pageable previousPageable() {
    return null;
  }

  @Override
  public Iterator<T> iterator() {
    return page.iterator();
  }

  @Override
  public <U> Page<U> map(Function<? super T, ? extends U> converter) {
    return null;
  }
}
