package dev.dubhe.libms.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.dubhe.libms.dao.IStudentDao;
import dev.dubhe.libms.mapper.StudentMapper;
import dev.dubhe.libms.mapper.domain.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentDaoImpl extends ServiceImpl<StudentMapper, Student> implements IStudentDao {
}
