package dev.dubhe.libms.mapper.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import dev.dubhe.libms.request.BookBody;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("Book")
public class Book {
    @TableId
    public Long bno;
    public String bname;
    public String bauthor;
    public String bversion;
    public String bpress;
    public Integer binventory;

    public static Book create(BookBody body) {
        return new Book(body.getBno(), body.getBname(), body.getBauthor(), body.getBversion(), body.getBpress(), body.getBinventory());
    }
}
