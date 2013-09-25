package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링의 테스트 컨텍스트 프레임워크의 JUnit 확장기능 지정 
@ContextConfiguration(locations="/applicationContext.xml") 
	//테스트 컨텍스트가 자동으로 만들어줄 애플리케이션 컨텍스트의 위치정

public class UserDaoTest {
	@Autowired
	private ApplicationContext context; // 테스트 오브젝트 생성후 자동으로 값 주입 
	
//	private UserDao dao;
	@Autowired
	UserDao dao;
	
	/** 2장  @before */
	@Before
	public void setUp(){
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		dao = context.getBean("userDao", UserDao.class);
		/** 2장 스프링 테스트 컨텍스트 프레임워크 적용 */ 
		this.dao = this.context.getBean("userDao",UserDao.class);
		
//		DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/springbook?characterEncoding=UTF-8","root","1030aa",true);
//		dao.setDataSource(dataSource);
		
	}
	
	/** 2장 2.2.2 -테스트 메소드 전환 */
	@Test
	public void addAndGet() throws SQLException {
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		UserDao dao = context.getBean("userDao", UserDao.class);
		
		/** 2장 2.3.2 -- deleteAll()과 getCount() 추가 */
		dao.deleteAll();

		User user = new User("id1","name1","pw1");

		dao.add(user);
			
		System.out.println(user.getId() + " 입력 성공");
				
//		User user2 = dao.get(user.getId());
		
		
		/**2장 2.2.2 -검증 코드 전환 */
//		assertThat(user2.getName(), is(user.getName()));
//		assertThat(user2.getPassword(), is(user.getPassword()));
		
	}
	
	@Test
	public void count() throws SQLException{
		/** 2장 2.3.3 -- 포괄적인 테스트 */
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user1 = new User("id1","name1","pw1");
		User user2 = new User("id2","name2","pw2");
		User user3 = new User("id3","name3","pw3");

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
	@Test(expected=EmptyResultDataAccessException.class) //테스트 중에 발생할 것으로 기대하는 예외 클래스 지정  
	public void getUserFailure() throws SQLException{
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		UserDao dao = context.getBean("userDao", UserDao.class);
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id");
		
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		/** 2장 2.2.2 -JUnit 테스트 실행*/
//		JUnitCore.main("springbook.user.dao.UserDaoTest");
		
		
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		UserDao dao = context.getBean("userDao", UserDao.class);
//		
//		User user = new User();
//		user.setId("jiyeon");
//		user.setName("");
//		user.setPassword("1111");
//
//		dao.add(user);
//			
//		System.out.println(user.getId() + " 입력 성공");
//		
//		User user2 = dao.get(user.getId());
//		/** 1장 */
////		System.out.println(user2.getName());
////		System.out.println(user2.getPassword());
////			
////		System.out.println(user2.getId() + " 조회 성공");
//		
//		/** 2장 2.2.1*/
//		if(!user.getName().equals(user2.getName())){
//			System.out.println("테스트 실패 (name)");
//		}else if(!user.getPassword().equals(user2.getPassword())){
//			System.out.println("테스트 실패 (password)");
//		}else {
//			System.out.println("조회 테스트 성공");
//		}
		
	}
	
	
	
	
}
