package com.example.hello.utils;

import com.example.hello.utils.GraphicUtil;
import com.example.hello.vo.PaperVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

public class GraphicUtilTest {

    private GraphicUtil graphicUtil = new GraphicUtil();

    private PaperVo paperVo = null;

    @Before
    public void before() {
        setKingmiReqDto();
    }

    @Test
    public void createCredentialTest() {
        assert graphicUtil.createCertificate(paperVo) != null;
    }

    private void setKingmiReqDto() {
        paperVo = new PaperVo();
        paperVo.setAmount(123456789L);
        paperVo.setCreatedDate(new Date());
        paperVo.setDueDate(new Date());
        paperVo.setIfTransfer(1);
        paperVo.setIfDelay(1);
        paperVo.setHolderEnterpriseName("宇宙金融科技有限公司");
        paperVo.setNote("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十");
    }
}
