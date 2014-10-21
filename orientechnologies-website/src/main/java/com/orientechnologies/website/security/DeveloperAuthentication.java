package com.orientechnologies.website.security;

import com.orientechnologies.website.model.schema.dto.Developer;
import com.orientechnologies.website.model.schema.dto.DeveloperAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Enrico Risa on 21/10/14.
 */
public class DeveloperAuthentication implements Authentication {

  private final Developer developer;
  private boolean         authenticated = true;

  public DeveloperAuthentication(Developer developer) {
    this.developer = developer;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new HashSet<GrantedAuthority>() {
      {
        add(DeveloperAuthority.baseDevelAuthority());
      }
    };
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean b) throws IllegalArgumentException {
    authenticated = b;
  }

  public String getGithubToken() {
    return developer.getToken();
  }

  @Override
  public String getName() {
    return developer.getLogin();
  }

  public Developer getDeveloper() {
    return developer;
  }
}
