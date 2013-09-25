package springbook.user.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.User;
import springbook.user.dao.*;

public class UserDao {
	private DataSource dataSource;
	private JdbcContext jdbcContext;
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcContext(JdbcContext jdbcContext){
		this.jdbcContext = jdbcContext;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
//		this.dataSource = dataSource;
	}

	public void add(final User user) throws SQLException { //final로 지정하면 로컬 클래스의 코드에서도 접근이 능해짐
		
		this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)"
				, user.getId(), user.getName(), user.getPassword());
	}

	public User get(String id) {		
		return this.jdbcTemplate.queryForObject("select * from users where id=?", new Object[] {id}, 
				new RowMapper<User>(){
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						return user;
					}
			
		});	
	}

	public List<User> getAll(){
		return this.jdbcTemplate.query("select * from users order by id", new RowMapper<User>(){
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		});
	}
	
	/** 2장 2.3.2 -deleteAll()과 getCount() 추가  */
	public void deleteAll() throws SQLException{
		
//		StatementStrategy st = new DeleteAllStatement();
//		jdbcContextWithStatementStrategy(st);
		
		this.jdbcTemplate.update("delete from users");
		
	}
	
//	/** 3장 메소드로 try/catch/finally 분리 */
//	public void jdbcContextWithStatementStrategy(StatementStrategy stat) throws SQLException {
//		Connection c = null;
//		PreparedStatement ps = null;
//		
//		/** 3장 3.1.1 -예외발생시 리소스  */
//		try {
//			c =  dataSource.getConnection();
//			
////			StatementStrategy strategy = new DeleteAllStatement();
//			ps = stat.makePreparedStatement(c);
//
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}finally{
//			if(ps != null){
//				try {
//					ps.close();
//				} catch (SQLException e) {
//					// TODO: handle exception
//				}
//			}
//			if(c != null){
//				try {
//					c.close();
//				} catch (SQLException e) {
//					// TODO: handle exception
//				}
//			}
//		}
//	}
	
	/** 3장 메소드 추출 */
	private PreparedStatement makeStatement(Connection c) throws SQLException{
		PreparedStatement ps;
		ps = c.prepareStatement("delete from users");
		return ps;
	}
	
	public int getCount(){
		return this.jdbcTemplate.query(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				
				return con.prepareStatement("select count(*) from users");
				
			}
		}, new ResultSetExtractor<Integer>() {
			public Integer extractData(ResultSet rs) throws SQLException {
				rs.next();
				return rs.getInt(1);
			}
		});
	}
}



