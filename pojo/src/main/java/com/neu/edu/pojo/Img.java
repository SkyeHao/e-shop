package com.neu.edu.pojo;

import com.neu.edu.pojo.BasePojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author hsk
 * @since 2021-07-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Img extends BasePojo {

    private static final long serialVersionUID = 1L;

    /**
     * 文件MD5
     */
    private String md5;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件后缀名
     */
    private String suffix;

    /**
     * 文件路径
     */
    private String url;


}
