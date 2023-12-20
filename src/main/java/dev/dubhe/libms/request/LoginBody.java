package dev.dubhe.libms.request;

import lombok.Data;

@Data
public class LoginBody {
    private final Long uid;
    private final String password;
}
