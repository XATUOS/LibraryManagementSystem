package dev.dubhe.libms.service;

import dev.dubhe.libms.dao.IBookDao;
import dev.dubhe.libms.mapper.BookMapper;
import dev.dubhe.libms.mapper.domain.Book;
import dev.dubhe.libms.mapper.domain.Student;
import dev.dubhe.libms.request.BookBody;
import dev.dubhe.libms.utils.WrapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final IBookDao bookDao;
    private final BookMapper bookMapper;

    public List<Book> getBooks() {
        return new ArrayList<>(this.bookDao.list());
    }

    public boolean add(BookBody body) {
        Book book = this.bookDao.getOne(WrapperUtil.eq("sno", body.bno));
        if (book != null) return false;
        book = Book.create(body);
        this.bookDao.saveOrUpdate(book);
        return true;
    }

    public boolean modify(BookBody body) {
        Book book = this.bookDao.getOne(WrapperUtil.eq("bno", body.bno));
        if (book == null) return false;
        book.setBno(body.getBno());
        book.setBname(body.getBname());
        book.setBauthor(body.getBauthor());
        book.setBversion(body.getBversion());
        book.setBpress(body.getBpress());
        book.setBinventory(body.getBinventory());
        this.bookDao.updateById(book);
        return true;
    }

    public boolean delete(Long bno) {
        Book book = this.bookDao.getOne(WrapperUtil.eq("bno", bno));
        if (book == null) return false;
        this.bookMapper.deleteById(book);
        return true;
    }
}
