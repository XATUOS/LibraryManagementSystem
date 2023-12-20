package dev.dubhe.libms.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.dubhe.libms.dao.IBorrowingDao;
import dev.dubhe.libms.mapper.BorrowingMapper;
import dev.dubhe.libms.mapper.domain.Borrowing;
import org.springframework.stereotype.Service;

@Service
public class BorrowingDaoImpl extends ServiceImpl<BorrowingMapper, Borrowing> implements IBorrowingDao {
}
