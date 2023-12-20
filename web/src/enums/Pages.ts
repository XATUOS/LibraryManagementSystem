import {Page} from "@/model/Page";
import {RouteRecordRaw} from "vue-router";
import {UserType} from "@/enums/UserType";

export class Pages {
    public static readonly BOOKS: Page = new Page("/books", "图书借阅", "BooksPage",
        UserType.STUDENT
    );
    public static readonly MY_BOOKS: Page = new Page("/my_books", "我的书单", "MyBooksPage",
        UserType.STUDENT
    );
    public static readonly STU_MANG: Page = new Page("/stu_mang", "学生管理", "StudentMangPage",
        UserType.TEACHER
    );
    public static readonly BOOKS_MANG: Page = new Page("/books_mang", "书籍管理", "BooksMangPage",
        UserType.TEACHER
    );
    public static readonly PAGES: Page[] = [
        Pages.BOOKS, Pages.MY_BOOKS, Pages.STU_MANG, Pages.BOOKS_MANG
    ];

    public static genRouter(type: UserType): RouteRecordRaw[] {
        let raw: RouteRecordRaw[] = []
        for (let page of Pages.PAGES) {
            if (!page.canAccess(type) || page.path.length == 0) continue;
            raw.push({
                path: page.link,
                name: page.name,
                component: () => import(`../views/${page.path}.vue`),
            })
        }
        return raw;
    }

    public static getPermissionPages(type: UserType): Page[] {
        let pages: Page[] = [];
        for (let page of Pages.PAGES) {
            if (!page.canAccess(type)) continue;
            if (page.path.length == 0) continue;
            pages.push(page)
        }
        return pages;
    }
}
