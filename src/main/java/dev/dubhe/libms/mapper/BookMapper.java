package dev.dubhe.libms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dev.dubhe.libms.mapper.domain.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
