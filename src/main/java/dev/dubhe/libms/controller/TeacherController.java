package dev.dubhe.libms.controller;

import com.alibaba.fastjson2.JSONObject;
import dev.dubhe.libms.data.RequestResult;
import dev.dubhe.libms.request.BookBody;
import dev.dubhe.libms.request.ChangePwdBody;
import dev.dubhe.libms.request.LoginBody;
import dev.dubhe.libms.request.StudentBody;
import dev.dubhe.libms.service.BookService;
import dev.dubhe.libms.service.StudentService;
import dev.dubhe.libms.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping("/api/tea")
public class TeacherController {
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final BookService bookService;

    @PostMapping("/login")
    public RequestResult login(@RequestBody LoginBody body) {
        JSONObject object = this.teacherService.login(body.getUid(), body.getPassword());
        return object == null ? new RequestResult(401, "账号或密码错误") : RequestResult.success(object);
    }

    @PostMapping("/pwd")
    public RequestResult pwd(@RequestBody ChangePwdBody body, HttpServletRequest request) {
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        if (!this.teacherService.verify(body.getUid(), token)) return new RequestResult(401, "登录异常");
        int hash = this.teacherService.pwd(body.getUid(), body.getPassword());
        return hash != -1 ? RequestResult.success(hash) : new RequestResult(401, "用户不存在");
    }

    @GetMapping("/all_stu")
    public RequestResult allStu(HttpServletRequest request) {
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        if (!this.teacherService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return RequestResult.success(this.studentService.getStudents());
    }

    @PutMapping("/add_stu")
    public RequestResult addStu(@RequestBody StudentBody body, HttpServletRequest request) {
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        if (!this.teacherService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return this.studentService.add(body) ? RequestResult.success() : new RequestResult(500, "操作异常");
    }

    @DeleteMapping("/remove_stu/{sno}")
    public RequestResult removeStu(@PathVariable Long sno, HttpServletRequest request) {
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        if (!this.teacherService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return this.studentService.delete(sno) ? RequestResult.success() : new RequestResult(500, "操作异常");
    }

    @PostMapping("/modify_stu")
    public RequestResult modifyStu(@RequestBody StudentBody body, HttpServletRequest request) {
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        if (!this.teacherService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return this.studentService.modify(body) ? RequestResult.success() : new RequestResult(500, "操作异常");
    }

    @PutMapping("/add_book")
    public RequestResult addBook(@RequestBody BookBody body, HttpServletRequest request) {
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        if (!this.teacherService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return this.bookService.add(body) ? RequestResult.success() : new RequestResult(500, "操作异常");
    }

    @DeleteMapping("/remove_book/{bno}")
    public RequestResult removeBook(@PathVariable Long bno, HttpServletRequest request) {
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        if (!this.teacherService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return this.bookService.delete(bno) ? RequestResult.success() : new RequestResult(500, "操作异常");
    }

    @PostMapping("/modify_book")
    public RequestResult modifyBook(@RequestBody BookBody body, HttpServletRequest request) {
        int token = Integer.parseInt(request.getHeader("AuthToken"));
        long uid = Long.parseLong(request.getHeader("AuthUid"));
        if (!this.teacherService.verify(uid, token)) return new RequestResult(401, "登录异常");
        return this.bookService.modify(body) ? RequestResult.success() : new RequestResult(500, "操作异常");
    }
}
