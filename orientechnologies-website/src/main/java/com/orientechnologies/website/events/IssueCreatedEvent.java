package com.orientechnologies.website.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import reactor.event.Event;

import com.orientechnologies.website.configuration.AppConfig;
import com.orientechnologies.website.model.schema.dto.Issue;
import com.orientechnologies.website.model.schema.dto.OUser;

/**
 * Created by Enrico Risa on 30/12/14.
 */
@Component
public class IssueCreatedEvent extends EventInternal<Issue> {

  @Autowired
  @Lazy
  protected JavaMailSenderImpl sender;

  @Autowired
  protected AppConfig          config;

  @Autowired
  private SpringTemplateEngine templateEngine;

  public static String         EVENT = "issue_created";

  @Override
  public String event() {
    return EVENT;
  }

  @Override
  public void accept(Event<Issue> issueEvent) {

    Issue issue = issueEvent.getData();
    Context context = new Context();
    fillContextVariable(context, issue);
    String htmlContent = templateEngine.process("newIssue.html", context);
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    OUser owner = issue.getScope().getOwner();
    mailMessage.setTo(owner.getEmail());
    mailMessage.setFrom("notification@prjhub.com");
    mailMessage.setSubject(issue.getTitle());
    mailMessage.setText(htmlContent);
    sender.send(mailMessage);

  }

  private void fillContextVariable(Context context, Issue issue) {
    context.setVariable("link", config.endpoint + "/#issues/" + issue.getIid());
    String body = null;
    if (issue.getBody() != null) {
      body = issue.getBody();
    } else {
      body = "Created issue ";
    }
    context.setVariable("link", body);
  }
}
