package com.github.vaatech.aom.util.common.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class LifecycleEntity<T extends Serializable> implements BaseLifecycleEntity<T>
{
  @Column(name = ColumnConstants.CREATED_BY, nullable = false, updatable = false)
  private String createdBy;

  @Column(name = ColumnConstants.CREATED_AT, nullable = false, updatable = false)
  private ZonedDateTime createdAt;

  @Column(name = ColumnConstants.UPDATED_BY, insertable = false)
  private String updatedBy;

  @Column(name = ColumnConstants.UPDATED_AT, insertable = false)
  private ZonedDateTime updatedAt;

  @Column(name = ColumnConstants.DELETED_BY, updatable = false, insertable = false)
  private String deletedBy;

  @Column(name = ColumnConstants.DELETED_AT, updatable = false, insertable = false)
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
