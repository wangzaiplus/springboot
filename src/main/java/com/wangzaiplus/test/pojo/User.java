package com.wangzaiplus.test.pojo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {

    private Integer id;
    private String username;
    private String password;

}
