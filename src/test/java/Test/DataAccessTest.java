package Test;

import static org.junit.Assert.*;
/*
 * Erabiltzailea ezabatu metodoa ez dudanez inplementatu, proba kasuetako datu basean dagoen erabiltzailea erabiliko dut.
 */

import java.io.EOFException;

import org.junit.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;

public class DataAccessTest {
	static DataAccess sut=new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("inicialize"));
	
	@Test
	public void test1() {
		try {
		sut.egiaztatuErabiltzailea(null, "123");
		fail();
	}catch(Exception e) {
		assertTrue(true);
	}

}
	@Test
	public void test2() {
		try {
		sut.egiaztatuErabiltzailea("asier", null);
		fail();
	}catch(Exception e) {
		assertTrue(true);
	}

} 
	@Test
	public void test3() {
		try {
			sut.open(true);
			sut.erabiltzaileaErregistratu("asier", "12345", true);
		boolean ona = sut.egiaztatuErabiltzailea("asier", "1234");
		assertEquals(ona,false);
		sut.close();
	}catch(Exception e) {
		fail();
		sut.close();
	}

}
	@Test
	public void test4() {
		try {
			sut.open(true);
		sut.erabiltzaileaErregistratu("asier", "12345", true);
		boolean ona = sut.egiaztatuErabiltzailea("asier", "12345");
		assertEquals(ona,true);
		sut.close();
	}catch(Exception e) {
		fail();
		sut.close();
	}

}
	@Test
	public void test5() {
		try {
			sut.open(true);
		sut.erabiltzaileaErregistratu("erabiltzailea", "ab1", false);
		boolean ona = sut.egiaztatuErabiltzailea("erabiltzailea", "abc");
		assertEquals(ona,false);
		sut.close();
	}catch(Exception e) {
		fail();
		sut.close();
	}

}

	@Test
	public void test6() {
		try {
			sut.open(true);
		sut.erabiltzaileaErregistratu("erabiltzailea", "ab1", false);
		boolean ona = sut.egiaztatuErabiltzailea("erabiltzailea", "ab1");
		assertEquals(ona,true);
		sut.close();
	}catch(Exception e) {
		fail();
		sut.close();
	}

}
	@Test
	public void test7() {
		try {
			sut.open(true);
		boolean ona = sut.egiaztatuErabiltzailea("erab", "kaixo");
		assertEquals(ona,false);
		sut.close();
	}catch(Exception e) {
		fail();
		sut.close();
	}

}
	
}
