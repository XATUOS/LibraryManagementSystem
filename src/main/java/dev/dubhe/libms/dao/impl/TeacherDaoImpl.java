package dev.dubhe.libms.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.dubhe.libms.dao.ITeacherDao;
import dev.dubhe.libms.mapper.TeacherMapper;
import dev.dubhe.libms.mapper.domain.Teacher;
import org.springframework.stereotype.Service;

@Service
public class TeacherDaoImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherDao {
}
