package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void workWithStatementStrategy(StatementStrategy stat) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		/** 3장 3.1.1 -예외발생시 리소스  */
		try {
			c =  dataSource.getConnection();
			
//			StatementStrategy strategy = new DeleteAllStatement();
			ps = stat.makePreparedStatement(c);

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
			if(c != null){
				try {
					c.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}
	

	

}
