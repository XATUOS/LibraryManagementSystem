package dev.dubhe.libms.controller;

import dev.dubhe.libms.data.RequestResult;
import dev.dubhe.libms.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    @GetMapping("/all")
    public RequestResult all() {
        return RequestResult.success(this.bookService.getBooks());
    }
}
