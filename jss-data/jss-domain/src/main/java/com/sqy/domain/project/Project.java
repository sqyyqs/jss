package com.sqy.domain.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Project {
    private Long id;
    private String code;
    private String name;
    /*todo @Nullable*/ private String description;
    private ProjectStatus projectStatus;
}
