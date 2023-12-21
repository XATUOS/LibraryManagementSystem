package dev.dubhe.libms.service;

import com.alibaba.fastjson2.JSONObject;
import dev.dubhe.libms.dao.IBookDao;
import dev.dubhe.libms.dao.IBookListDao;
import dev.dubhe.libms.dao.IBorrowingDao;
import dev.dubhe.libms.dao.IStudentDao;
import dev.dubhe.libms.mapper.BookListMapper;
import dev.dubhe.libms.mapper.BorrowingMapper;
import dev.dubhe.libms.mapper.StudentMapper;
import dev.dubhe.libms.mapper.domain.Book;
import dev.dubhe.libms.mapper.domain.BookList;
import dev.dubhe.libms.mapper.domain.Borrowing;
import dev.dubhe.libms.mapper.domain.Student;
import dev.dubhe.libms.request.StudentBody;
import dev.dubhe.libms.utils.WrapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final IStudentDao studentDao;
    private final StudentMapper studentMapper;
    private final IBookDao bookDao;
    private final BorrowingMapper borrowingMapper;
    private final IBorrowingDao borrowingDao;
    private final IBookListDao bookListDao;
    private final BookListMapper bookListMapper;

    public List<JSONObject> getStudents() {
        List<JSONObject> list = new ArrayList<>();
        for (Student student : this.studentDao.list()) {
            JSONObject object = JSONObject.from(student);
            object.remove("spassword");
            list.add(object);
        }
        return list;
    }

    public JSONObject login(Long uid, String password) {
        Student student = this.studentDao.getOne(WrapperUtil.eq("sno", uid));
        if (student == null || !student.getSpassword().equals(password)) return null;
        int token = student.getSpassword().hashCode();
        JSONObject object = new JSONObject();
        object.put("token", String.valueOf(token));
        object.put("username", student.getSname());
        return object;
    }

    public int pwd(Long uid, String password) {
        Student student = this.studentDao.getOne(WrapperUtil.eq("sno", uid));
        if (student != null) {
            student.setSpassword(password);
            this.studentDao.updateById(student);
            return password.hashCode();
        }
        return -1;
    }

    public boolean verify(Long uid, int hash) {
        Student student = this.studentDao.getOne(WrapperUtil.eq("sno", uid));
        if (student != null) {
            return student.getSpassword().hashCode() == hash;
        }
        return false;
    }


    public List<JSONObject> getBorrowingList(Long uid) {
        List<JSONObject> books = new ArrayList<>();
        for (Borrowing bookList : this.borrowingDao.list(WrapperUtil.eq("sno", uid, "del", 0))) {
            JSONObject object;
            Book book = this.bookDao.getOne(WrapperUtil.eq("bno", bookList.getBno()));
            if (book != null) {
                object = JSONObject.from(book);
                object.put("id", bookList.getId());
                object.put("borrowing_date", bookList.getBorrowingDate());
                object.put("return_date", bookList.getReturnDate());
                books.add(object);
            }
        }
        return books;
    }

    public boolean borrow(Long uid, Long bid) {
        Student student = this.studentDao.getOne(WrapperUtil.eq("sno", uid));
        Book book = this.bookDao.getOne(WrapperUtil.eq("bno", bid));
        if (student == null || book == null || book.getBinventory() < 1) return false;
        List<Borrowing> borrowings = this.borrowingDao.list(WrapperUtil.eq("sno", uid, "bno", bid));
        for (Borrowing borrowing : borrowings) {
            if (borrowing != null && borrowing.getReturnDate() == null) return false;
        }
        Borrowing borrowing = Borrowing.builder()
                .sno(uid)
                .bno(bid)
                .version(0)
                .del(false)
                .borrowingDate(new Date())
                .returnDate(null)
                .build();
        book.setBinventory(book.getBinventory() - 1);
        this.borrowingMapper.insert(borrowing);
        this.bookDao.saveOrUpdate(book);
        return true;
    }

    public boolean unBorrow(Long uid, Long bid) {
        Student student = this.studentDao.getOne(WrapperUtil.eq("sno", uid));
        if (student == null) return false;
        Borrowing borrowing = this.borrowingDao.getOne(WrapperUtil.eq("sno", uid, "id", bid));
        if (borrowing == null || borrowing.getReturnDate() != null) return false;
        Book book = this.bookDao.getOne(WrapperUtil.eq("bno", borrowing.getBno()));
        if (book == null) return false;
        borrowing.setReturnDate(new Date());
        book.setBinventory(book.getBinventory() + 1);
        this.bookDao.saveOrUpdate(book);
        this.borrowingDao.saveOrUpdate(borrowing);
        return true;
    }

    public List<Book> getBookList(Long uid) {
        List<Book> books = new ArrayList<>();
        for (BookList bookList : this.bookListDao.list(WrapperUtil.eq("sno", uid, "del", 0))) {
            Book book = this.bookDao.getOne(WrapperUtil.eq("bno", bookList.getBno()));
            if (book != null) books.add(book);
        }
        return books;
    }

    public boolean addBookList(Long uid, Long bid) {
        Student student = this.studentDao.getOne(WrapperUtil.eq("sno", uid));
        Book book = this.bookDao.getOne(WrapperUtil.eq("bno", bid));
        if (student == null || book == null) return false;
        BookList bookList = this.bookListDao.getOne(WrapperUtil.eq("sno", uid, "bno", bid));
        if (bookList != null) {
            bookList.setDel(false);
            this.bookListDao.saveOrUpdate(bookList);
        } else {
            bookList = BookList.builder()
                    .sno(uid)
                    .bno(bid)
                    .version(0)
                    .del(false)
                    .build();
            this.bookListMapper.insert(bookList);
        }
        return true;
    }

    public boolean removeBookList(Long uid, Long bid) {
        Student student = this.studentDao.getOne(WrapperUtil.eq("sno", uid));
        Book book = this.bookDao.getOne(WrapperUtil.eq("bno", bid));
        if (student == null || book == null) return false;
        BookList bookList = this.bookListDao.getOne(WrapperUtil.eq("sno", uid, "bno", bid));
        if (bookList == null) return false;
        bookList.setDel(true);
        this.bookListDao.saveOrUpdate(bookList);
        return true;
    }

    public boolean add(StudentBody body) {
        Student student = this.studentDao.getOne(WrapperUtil.eq("sno", body.sno));
        if (student != null) return false;
        student = Student.create(body);
        this.studentDao.saveOrUpdate(student);
        return true;
    }

    public boolean modify(StudentBody body) {
        Student student = this.studentDao.getOne(WrapperUtil.eq("sno", body.sno));
        if (student == null) return false;
        student.setSno(body.getSno());
        student.setSname(body.getSname());
        student.setSsex(body.getSsex());
        student.setSmajor(body.getSmajor());
        student.setScollege(body.getScollege());
        student.setSpassword(body.getSpassword());
        this.studentDao.updateById(student);
        return true;
    }

    public boolean delete(Long sno) {
        Student student = this.studentDao.getOne(WrapperUtil.eq("sno", sno));
        if (student == null) return false;
        this.studentMapper.deleteById(student);
        return true;
    }
}
