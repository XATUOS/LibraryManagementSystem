package dev.dubhe.libms.request;

import lombok.Data;

@Data
public class ChangePwdBody {
    public final Long uid;
    public final String password;
}
