package com.example.hello.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhujianwei
 * @version 1.0
 * date: 2019-05-05
 */
@Data
public class PaperVo {
    /**
     * 银票id
     */
    private Long id;

    /**
     * 银票编号（开立/转让）
     */
    private String encode;

    /**
     * 持有企业id
     */
    private Long holderEnterpriseId;

    /**
     * 持有企业名称
     */
    private String holderEnterpriseName;

    /**
     * 银票金额，单位分
     */
    private Long amount;

    /**
     * 银票开立/转让日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createdDate;

    /**
     * 银票到期日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dueDate;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否转让, 0:否， 1:是
     */
    private Integer ifTransfer;

    /**
     * 是否延期, 0:否， 1:是
     */
    private Integer ifDelay;



    /**
     * 转让方名字
     */
    private String primaryEnterpriseName;
}
