package com.itwillbs.web;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class MysqlConnectTest {

	// 디비연결정보
	private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/springdb";
	private static String DBID = "root";
	private static String DBPW = "1234";

	@Test
	public void dbConnectTest() {

//		try {
//			// 디비연결
//			// 1. 드라이버 로드
//			Class.forName(DRIVER);
//			System.out.println(" 드라이버 로드 완료");
//			// 2. 디비 연결
//			DriverManager.getConnection(URL, DBID, DBPW);
//			System.out.println(" 디비 연결 성공!");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		// 1. 드라이버 로드
		try {
			Class.forName(DRIVER);
			System.out.println(" 드라이버 로드 완료");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		// try-with 구문 : try()있는 객체의 자원을 자동으로 해제
		// JDK 1.7이상 가능 (AutoCloseable 인터페이스를 상속한 객체만)
		try (Connection con = DriverManager.getConnection(URL, DBID, DBPW);) {
			// 디비연결
			// 2. 디비 연결

			System.out.println(" 디비 연결 성공!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
