package dev.dubhe.libms.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.dubhe.libms.dao.IBookListDao;
import dev.dubhe.libms.mapper.BookListMapper;
import dev.dubhe.libms.mapper.domain.BookList;
import org.springframework.stereotype.Service;

@Service
public class BookListDaoImpl extends ServiceImpl<BookListMapper, BookList> implements IBookListDao {
}
