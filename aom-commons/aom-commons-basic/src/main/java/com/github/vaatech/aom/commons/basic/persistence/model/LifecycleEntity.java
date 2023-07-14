package com.github.vaatech.aom.commons.basic.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class LifecycleEntity<T extends Serializable> implements BaseLifecycleEntity<T>
{
  public interface Persistence {
    String CREATED_BY = "created_by";
    String CREATED_AT = "created_at";
    String UPDATED_BY = "updated_by";
    String UPDATED_AT = "updated_at";
    String DELETED_BY = "deleted_by";
    String DELETED_AT = "deleted_at";
  }

  @Column(name = Persistence.CREATED_BY, nullable = false, updatable = false)
  private String createdBy;

  @Column(name = Persistence.CREATED_AT, nullable = false, updatable = false)
  private ZonedDateTime createdAt;

  @Column(name = Persistence.UPDATED_BY, insertable = false)
  private String updatedBy;

  @Column(name = Persistence.UPDATED_AT, insertable = false)
  private ZonedDateTime updatedAt;

  @Column(name = Persistence.DELETED_BY, updatable = false, insertable = false)
  private String deletedBy;

  @Column(name = Persistence.DELETED_AT, updatable = false, insertable = false)
  private ZonedDateTime deletedAt;

  public String getCreatedBy() {
    return createdBy;
  }

  @Override
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  @Override
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public void setUpdatedAt(ZonedDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getDeletedBy() {
    return deletedBy;
  }

  @Override
  public void setDeletedBy(String deletedBy) {
    this.deletedBy = deletedBy;
  }

  public ZonedDateTime getDeletedAt() {
    return deletedAt;
  }

  @Override
  public void setDeletedAt(ZonedDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  public boolean isDeleted() {
    return deletedAt != null;
  }
}
