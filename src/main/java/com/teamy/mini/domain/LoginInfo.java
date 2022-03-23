package com.teamy.mini.domain;


import com.sun.istack.NotNull;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
