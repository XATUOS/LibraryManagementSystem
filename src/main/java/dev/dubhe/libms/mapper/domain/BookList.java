package dev.dubhe.libms.mapper.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("BookList")
public class BookList {
    @TableId(type = IdType.INPUT)
    private Long sno;
    @TableField(insertStrategy = FieldStrategy.IGNORED)
    private Long bno;
    @Version
    protected Integer version;
    protected Boolean del;

    public BookList(Long sno, Long bno, Integer version, Boolean del) {
        this.sno = sno;
        this.bno = bno;
        this.version = version;
        this.del = del;
    }
}
