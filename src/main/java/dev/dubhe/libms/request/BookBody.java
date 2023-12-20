package dev.dubhe.libms.request;

import lombok.Data;

@Data
public class BookBody {
    public final Long bno;
    public final String bname;
    public final String bauthor;
    public final String bversion;
    public final String bpress;
    public final Integer binventory;
}
