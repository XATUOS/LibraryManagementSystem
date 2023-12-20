package dev.dubhe.libms.request;

import lombok.Data;

@Data
public class StudentBody {
    public final Long sno;
    public final String sname;
    public final String ssex;
    public final String smajor;
    public final String scollege;
    public final String spassword;
}
