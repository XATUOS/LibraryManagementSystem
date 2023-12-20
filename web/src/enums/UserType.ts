export enum UserType {
    TEACHER,
    STUDENT
}

export function getUserType(): UserType {
    return localStorage.getItem("userType") == "stu" ? UserType.STUDENT : UserType.TEACHER;
}
