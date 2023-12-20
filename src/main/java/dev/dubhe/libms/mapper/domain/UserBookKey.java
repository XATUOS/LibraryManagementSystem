package dev.dubhe.libms.mapper.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserBookKey implements Serializable {
    private Long sno;
    private Long bno;

    public UserBookKey(Long sno, Long bno) {
        this.sno = sno;
        this.bno = bno;
    }
}
