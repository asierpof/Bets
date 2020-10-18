package Test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;

@RunWith(MockitoJUnitRunner.class)
public class FacadeMockTest {
	DataAccess dataAccess=Mockito.mock(DataAccess.class);
	
	@InjectMocks
	 BLFacade sut=new BLFacadeImplementation(dataAccess);
	
	@Test
	public void test1() {
		try {
			Mockito.doReturn(false).when(dataAccess).egiaztatuErabiltzailea(null, "123");
		sut.egiaztatuErab(null, "123");
		fail();
	}catch(Exception e) {
		assertTrue(true);
	}

}
	@Test
	public void test2() {
		try {
			Mockito.doReturn(false).when(dataAccess).egiaztatuErabiltzailea("asier", null);
		sut.egiaztatuErab("asier", null);
		fail();
	}catch(Exception e) {
		assertTrue(true);
	}

} 
	@Test
	public void test3() {
		try {
			Mockito.doReturn(false).when(dataAccess).egiaztatuErabiltzailea("asier", "1234");
		boolean ona = sut.egiaztatuErab("asier", "1234");
		assertEquals(ona,false);
	}catch(Exception e) {
		fail();
	}

}
	@Test
	public void test4() {
		try {
			Mockito.doReturn(true).when(dataAccess).egiaztatuErabiltzailea("asier", "12345");
		boolean ona = sut.egiaztatuErab("asier", "12345");
		assertEquals(ona,true);
	}catch(Exception e) {
		fail();
	}

}
	@Test
	public void test5() {
		try {
			Mockito.doReturn(false).when(dataAccess).egiaztatuErabiltzailea("erabiltzailea", "abc");
		boolean ona = sut.egiaztatuErab("erabiltzailea", "abc");
		assertEquals(ona,false);
	}catch(Exception e) {
		fail();
	}

}

	@Test
	public void test6() {
		try {
			Mockito.doReturn(true).when(dataAccess).egiaztatuErabiltzailea("erabiltzailea", "ab1");
		boolean ona = sut.egiaztatuErab("erabiltzailea", "ab1");
		assertEquals(ona,true);
	}catch(Exception e) {
		fail();
	}

}
	@Test
	public void test7() {
		try {
			Mockito.doReturn(false).when(dataAccess).egiaztatuErabiltzailea("erab", "kaixo");
		boolean ona = sut.egiaztatuErab("erab", "kaixo");
		assertEquals(ona,false);
	}catch(Exception e) {
		fail();
	}

}

}
