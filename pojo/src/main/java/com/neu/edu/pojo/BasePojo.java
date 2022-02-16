package com.neu.edu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class BasePojo {
    @TableId(type = IdType.AUTO)
    private int id;
}
