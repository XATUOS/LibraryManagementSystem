package dev.dubhe.libms.mapper.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("Teacher")
public class Teacher {
    @TableId
    private Long tno;
    private String tname;
    private String tcollege;
    private String tpassword;
}
