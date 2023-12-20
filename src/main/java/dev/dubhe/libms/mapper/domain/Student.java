package dev.dubhe.libms.mapper.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import dev.dubhe.libms.request.StudentBody;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@TableName("Student")
public class Student {
    @TableId
    private Long sno;
    private String sname;
    private String ssex;
    private String smajor;
    private String scollege;
    private String spassword;

    public static Student create(StudentBody body){
        return new Student(body.getSno(), body.getSname(), body.getSsex(), body.getSmajor(), body.getScollege(), body.getSpassword());
    }
}
