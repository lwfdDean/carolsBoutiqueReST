/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.paymentGateway;

/**
 *
 * @author 27609
 */
public class Encryptor {
    public String Encrypt( String msgToEncrypt){
        char[] ch = new char[msgToEncrypt.length()];
        for (int i = 0; i < msgToEncrypt.length(); i++) {
            ch[i] = (char) (msgToEncrypt.charAt(i)+5);
        }
        return new String(ch);
    }
    public String Decrypt( String msgToDecrypt){
        char[] ch = new char[msgToDecrypt.length()];
        for (int i = 0; i < msgToDecrypt.length(); i++) {
            ch[i] = (char) (msgToDecrypt.charAt(i)-5);
        }
        return new String(ch);
    }
    
}
