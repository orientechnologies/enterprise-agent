package com.orientechnologies.website.model.schema.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Enrico Risa on 24/10/14.
 */
public class Issue {

  private String             id;
  private Integer            number;
  private String             title;
  private String             body;
  private String             state;
  private Collection<String> labels = new ArrayList<String>();
  private Repository         repository;

  private Date               createdAt;
  private Date               closedAt;

  private OUser user;
  private OUser assignee;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Collection<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getClosedAt() {
    return closedAt;
  }

  public void setClosedAt(Date closedAt) {
    this.closedAt = closedAt;
  }

  public OUser getAssignee() {
    return assignee;
  }

  public void setAssignee(OUser assignee) {
    this.assignee = assignee;
  }

  public void addLabel(String name) {
    labels.add(name);
  }

  public OUser getUser() {
    return user;
  }

  public void setUser(OUser user) {
    this.user = user;
  }

  public Repository getRepository() {
    return repository;
  }

  public void setRepository(Repository repository) {
    this.repository = repository;
  }
}