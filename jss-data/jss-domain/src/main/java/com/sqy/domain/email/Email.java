package com.sqy.domain.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Email {
    private String recipient;
    private String subject;
    private String html;
}
