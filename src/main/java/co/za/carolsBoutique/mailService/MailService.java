/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.mailService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService{
	private String type;
	private Object source;
	private String recipient;
	private Properties properties;
	private Session session;
	private Message message;

	public MailService(String type, Object source, String recipient) {
		this.type = type;
		this.source = source;
		this.recipient = recipient;
		this.properties = System.getProperties();
	}
	
	public void runMail() {
		try {
			Map<String, String> authenticationInfo = readInFromAuthentication();
			String email = authenticationInfo.keySet().iterator().next();
			configureEmail(email, authenticationInfo.get(email));
			message = new MimeMessage(session);
			switch (type) {
				case "stock":
					source = (String) source;
					message.setFrom(new InternetAddress(email));
					message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
					message.setSubject(type + " Carol's Boutique");
					message.setText(buildMessage("C:\\Users\\wille\\OneDrive\\Desktop\\emailTemplateReceipt.txt"));
					break;
//				case "reciept":
//					//cast to sale
//					message.setFrom(new InternetAddress(email));
//					message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
//					message.setSubject(type + " Carol's Boutique");
//					message.setText();
//					break;
				case "exchnage":
					break;
				case "refund":
					break;
				case "keep-Aside":
					break;
				case "requestedIBT":
					break;
				case "approvedIBT":
					break;
				case "customerIBT":
					break;
				case "newsLetter":
					break;
			}
		} catch (MessagingException ex) {
			Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("sending...");
		try {
			Transport.send(message);
		} catch (MessagingException ex) {
			Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Sent message successfully....");
	}

	private Map<String, String> readInFromAuthentication() {
		Map<String, String> map = new HashMap<>();
		map.put("carolsboutiquelwfd@yahoo.com", "fyrk rvgr oigx qvcn");//fyrk rvgr oigx qvcn
		return map;
	}

	private synchronized void configureEmail(String email, String password) {
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", "smtp.mail.beatmax.co");
		properties.put("mail.smtp.port", "587");
		session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		});
		session.setDebug(true);
	}

	private synchronized String buildMessage(String fileLocation) {
		source = (String) source;
		StringBuilder sb = new StringBuilder();
		File file = new File(fileLocation);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					sb.append("\n");
					continue;
				}
				if (line.equals("@")) {
					break;
				}
				if (line.isEmpty()) {
					sb.append(line);
					continue;
				}
				line = line + " " + source + "\n";
				sb.append(line);
			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return sb.toString();
	}


}
