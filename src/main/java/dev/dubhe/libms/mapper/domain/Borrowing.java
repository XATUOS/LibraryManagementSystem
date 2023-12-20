package dev.dubhe.libms.mapper.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@TableName("Borrowing")
@EqualsAndHashCode
@Builder
public class Borrowing {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long sno;
    private Long bno;
    @TableField(value = "borrowing_date", insertStrategy = FieldStrategy.IGNORED)
    private Date borrowingDate;
    @TableField("return_date")
    private Date returnDate;
    @Version
    private Integer version;
    private Boolean del;
}
