package dev.dubhe.libms.controller;

import com.alibaba.fastjson2.JSONObject;
import dev.dubhe.libms.data.RequestResult;
import dev.dubhe.libms.request.BookListBody;
import dev.dubhe.libms.request.ChangePwdBody;
import dev.dubhe.libms.request.LoginBody;
import dev.dubhe.libms.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping("/api/stu")
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/login")
    public RequestResult login(@RequestBody LoginBody body) {
        JSONObject object = this.studentService.login(body.getUid(), body.getPassword());
        return object == null ? new RequestResult(401, "账号或密码错误") : RequestResult.success(object);
    }

    @PostMapping("/pwd")
    public RequestResult pwd(@RequestBody ChangePwdBody body, HttpServletRequest request) {
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        if (!this.studentService.verify(body.getUid(), token)) return new RequestResult(401, "登录异常");
        int hash = this.studentService.pwd(body.getUid(), body.getPassword());
        return hash != -1 ? RequestResult.success(hash) : new RequestResult(401, "用户不存在");
    }

    @GetMapping("/book_list/get")
    public RequestResult getBookList(HttpServletRequest request) {
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        if (!this.studentService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return RequestResult.success(this.studentService.getBookList(uid));
    }

    @PostMapping("/book_list/add")
    public RequestResult addBookList(@RequestBody BookListBody body, HttpServletRequest request) {
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        if (!this.studentService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return this.studentService.addBookList(body.getUid(), body.getBid()) ? RequestResult.success() : new RequestResult(500, "操作异常");
    }

    @PostMapping("/book_list/remove")
    public RequestResult removeBookList(@RequestBody BookListBody body, HttpServletRequest request) {
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        if (!this.studentService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return this.studentService.removeBookList(body.getUid(), body.getBid()) ? RequestResult.success() : new RequestResult(500, "操作异常");
    }

    @GetMapping("/borrowing/get")
    public RequestResult getBorrowing(HttpServletRequest request) {
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        if (!this.studentService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return RequestResult.success(this.studentService.getBorrowingList(uid));
    }

    @PostMapping("/borrowing/add")
    public RequestResult addBorrowing(@RequestBody BookListBody body, HttpServletRequest request) {
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        if (!this.studentService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return this.studentService.borrow(body.getUid(), body.getBid()) ? RequestResult.success() : new RequestResult(500, "操作异常");
    }

    @PostMapping("/borrowing/remove")
    public RequestResult removeBorrowing(@RequestBody BookListBody body, HttpServletRequest request) {
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        if (!this.studentService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return this.studentService.unBorrow(body.getUid(), body.getBid()) ? RequestResult.success() : new RequestResult(500, "操作异常");
    }
}
