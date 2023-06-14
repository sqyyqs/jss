package com.sqy.domain.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Email {
    private String recipient;
    private String subject;
    private String html;
}
