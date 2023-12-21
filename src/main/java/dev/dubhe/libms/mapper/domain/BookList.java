package dev.dubhe.libms.mapper.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("BookList")
public class BookList {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long sno;
    private Long bno;
    @Version
    protected Integer version;
    protected Boolean del;
}
