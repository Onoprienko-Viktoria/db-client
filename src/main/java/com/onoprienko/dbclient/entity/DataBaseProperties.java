package com.onoprienko.dbclient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataBaseProperties {
    private final String dbUrl;
    private final String user;
    private final String password;
}
