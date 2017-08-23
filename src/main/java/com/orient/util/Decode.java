package com.orient.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

/*
    鉴于java 不支持PKCS7Padding，只支持PKCS5Padding 但是PKCS7Padding 和 PKCS5Padding 没有什么区别
     要实现在java端用PKCS7Padding填充，需要用到bouncycastle组件来实现
 */
public class Decode {

	// 算法名称
	final String KEY_ALGORITHM = "DES";
	// 加解密算法/模式/填充方式final
	String algorithmStr = "DES/CBC/PKCS7Padding";
	private Key key;
	private Cipher cipher;
	boolean isInited = false;

	// this.mCSP.CreateDecryptor(Convert.FromBase64String("FwGQWRRgKCI="),
	// Convert.FromBase64String("kXwL7X2+fgm="));

	BASE64Decoder decoder = new BASE64Decoder();
	byte[] rgbKey = null;
	byte[] rgbIv = null;

	public void doInit() throws Exception {
		rgbKey = decoder.decodeBuffer("FwGQWRRgKCI=");// rgbKey
		rgbIv = decoder.decodeBuffer("kXwL7X2+fgm=");// rgbIV

	}

	public void init(byte[] keyBytes) {
		// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
		int base = 8;
		if (keyBytes.length % base != 0) {
			int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
			byte[] temp = new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
			keyBytes = temp;
		}
		// 初始化
		Security.addProvider(new BouncyCastleProvider());
		// 转化成JAVA的密钥格式
		key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
		try {
			// 初始化cipher
			cipher = Cipher.getInstance(algorithmStr, "BC");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
	}

	/**
	 * 加密方法
	 *
	 * @param content
	 *            要加密的字符串
	 * @param keyBytes
	 *            加密密钥
	 * @return
	 */
	public byte[] encrypt(byte[] content, byte[] keyBytes) {
		byte[] encryptedText = null;
		init(keyBytes);
		//System.out.println("IV：" + new String(rgbIv));
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(rgbIv));
			encryptedText = cipher.doFinal(content);
		} catch (Exception e) {
		}
		return encryptedText;
	}

	public byte[] encrypt(byte[] content) {
		return encrypt(content, rgbKey);
	}

	/**
	 * 解密方法
	 *
	 * @param encryptedData
	 *            要解密的字符串
	 * @param keyBytes
	 *            解密密钥
	 * @return
	 */
	public byte[] decrypt(byte[] encryptedData, byte[] keyBytes) {
		byte[] encryptedText = null;
		init(keyBytes);
		try {
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(rgbIv));
			encryptedText = cipher.doFinal(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedText;
	}

	public byte[] decrypt(byte[] encryptedData) {
		return decrypt(encryptedData, rgbKey);
	}

	/**
	 * 加密过程(重点人群和进京人群soap体字段加密后传送)
	 * 
	 * @param str
	 * @return
	 * @throws Exception 
	 */
	public static String Jiami(String str) throws Exception {
		String zz = "";
		Decode decodeTest = new Decode();
		decodeTest.doInit();
		byte[] yy = decodeTest.encrypt(str.getBytes("utf-8"));
		BASE64Encoder encoder = new BASE64Encoder();
		zz = encoder.encodeBuffer(yy);
		return zz;
	}

	public static void main(String[] args) throws Exception {
		// Decode decodeTest = new Decode();
		// decodeTest.doInit();
		// BASE64Decoder decoder = new BASE64Decoder();
		// String et =
		// "k17LKO5UC+/Y+P+e1EV9nGJnim6MIwXR4K90Y0lRKfoZuRyJxRzHxOk1+TBsDtN7GfDgr4TS4uTLX3Ypc+bVeJFA80j09iKsTtjwN3Hiy+BFS/x7c/j8nxPoGdY5xRmSiSrDPrHw5XS3imGgSn5E3OKyKohwO2j35ZOemAANZKH0RHrJdaCV6kZ0cpQbWP+T/YNSRKGP+09cEMAKzTpeWjRoCwiHu9dIm7e/3A0wIyYKdsQbKAJU2rbp15a6Ih/sEnmB+t5J4vKS3vFRN/vVh1zDI/RPc8ZCkLMS7qmMa1YpfJvNpwfc1mNGS+66PlxUWdW/OoHpMTTPkE5EV1XD/WIA8Qlf3kt6oadUmPl+wFPBhKhlN8KLeZIYAscVRwgzkQDFo+9/iflfazDRxPOYIxy+jQwL9KzuNm56VQb6C2YtynlrlTu8cTVi/ly2PEP5c6ANshE+QF3U6JZZL4Ht+CS6OUzP0YWlIeVHDnOoAxP48ZxoTObQKg==";
		// byte[] encryptedData = decoder.decodeBuffer(et);
		// System.err.println(encryptedData.length);
		// byte[] x = decodeTest.decrypt(encryptedData);
		// String xxx = new String(x, "utf-8");
		// System.err.println(xxx);
		// String test = "id,NAME ,SEX,IDCARD ,PERSONNELAREA
		// ,CONTROLLEVEL,PERSONTYPE,jbsj,ZYSBWJB,gjjjb,sjb,shijb,gjjgj,ext2";
		// String test2 = "1=1 and RYLX=0 and PERSONNELAREA like '%无锡新区%'";
		// // 加密
		// byte[] yy = decodeTest.encrypt(test.getBytes());
		// BASE64Encoder encoder = new BASE64Encoder();
		// String zz = encoder.encodeBuffer(yy);
		// System.err.println(zz);
		// String result = Jiami(test2);
		// System.out.println(result);
	}

}
