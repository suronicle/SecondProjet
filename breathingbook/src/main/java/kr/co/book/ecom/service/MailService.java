package kr.co.book.ecom.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import kr.co.book.ecom.dao.JbookDAO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class  MailService{

	private final Properties serverInfo; // 서버 정보
	private final Authenticator auth; // 인증 정보
	//    RedisService redisService;

	public MailService() {

		// 네이버 SMTP 서버 접속 정보
		serverInfo = new Properties();
		serverInfo.put("mail.smtp.host", "smtp.gmail.com");
		serverInfo.put("mail.smtp.port", "587");
		serverInfo.put("mail.smtp.starttls.enable", "true");
		serverInfo.put("mail.smtp.auth", "true");
		serverInfo.put("mail.smtp.debug", "true");
		serverInfo.put("mail.smtp.socketFactory.port", "465"); //네이버 경로 이용
		serverInfo.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		serverInfo.put("mail.smtp.socketFactory.fallback", "false");

		// 네이버 서버와 ssl 통신이 되지 않을 경우 추가
		serverInfo.put("mail.smtp.ssl.protocols", "TLSv1.2");

		// 사용자 인증 정보
		auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("jeonem98@gmail.com", "hdingzxfrjynyjxy");
			}
		};
	}

	// 주어진 메일 내용을 네이버 SMTP 서버를 통해 전송
	public String emailSending(String to) throws MessagingException {
		// 1. 세션 생성
		Session session = Session.getInstance(serverInfo, auth);
		session.setDebug(true);

		// 2. 메시지 작성
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("jeonem98@gmail.com")); // 보내는 사람
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 받는 사람
		msg.setSubject("[숨쉬는서재] 인증코드 메일입니다."); // 제목
		String code = createCode();
		msg.setContent("인증코드:"+code, "text/html; charset=utf-8"); // 내용

		// 3. 전송
		Transport.send(msg);

		return code;
	}


	//랜덤 코드 실행
	private String createCode() {
		int lenth = 6;
		try {
			Random random = SecureRandom.getInstanceStrong();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < lenth; i++) {
				builder.append(random.nextInt(10));
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException e) {

			return "오류";
			//예외시 해결할 것
		}
	}


	// 비밀번호를 이메일로 전송
	public void emailpw(String to, String pw) throws MessagingException {

		// 1. 세션 생성
		Session session = Session.getInstance(serverInfo, auth);
		session.setDebug(true);

		// 2. 메시지 작성
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("jeonem98@gmail.com")); // 보내는 사람
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 받는 사람
		msg.setSubject("[숨쉬는서재] 비밀번호 안내."); // 제목
		msg.setContent("찾으신 비밀번호는 ["+pw+"]입니다.", "text/html; charset=utf-8"); // 내용

		// 3. 전송
		Transport.send(msg);
	}


}
