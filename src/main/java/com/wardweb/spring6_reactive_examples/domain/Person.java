package com.wardweb.spring6_reactive_examples.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Integer id;
    private String firstName;
    private String lastName;
}
