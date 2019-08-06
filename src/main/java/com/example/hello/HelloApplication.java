package com.example.hello;

import com.example.hello.utils.GraphicUtil;
import com.example.hello.vo.PaperVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class HelloApplication {

	@Autowired
	private GraphicUtil graphicUtil;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@Bean
	CommandLineRunner sampleCommandLineRunner() {
		return args -> {
			PaperVo paperVo = new PaperVo();
			paperVo.setAmount(123456789L);
			paperVo.setEncode("33AACCCCCCFFFFFFFFHHHH");
			paperVo.setCreatedDate(new Date());
			paperVo.setDueDate(new Date());
			paperVo.setIfTransfer(1);
			paperVo.setIfDelay(1);
			paperVo.setHolderEnterpriseName("宇宙金融科技有限公司");
			paperVo.setNote("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十");

			graphicUtil.createCertificate(paperVo);
			System.out.println("hello world.");
		};
	}
}
