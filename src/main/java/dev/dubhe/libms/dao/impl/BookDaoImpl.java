package dev.dubhe.libms.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.dubhe.libms.dao.IBookDao;
import dev.dubhe.libms.mapper.BookMapper;
import dev.dubhe.libms.mapper.domain.Book;
import org.springframework.stereotype.Service;

@Service
public class BookDaoImpl extends ServiceImpl<BookMapper, Book> implements IBookDao {
}
