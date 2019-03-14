package background;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class Sign
 */
@WebServlet("/Sign")
public class Sign extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String driver = "com.mysql.jdbc.Driver";
	static final String url = "jdbc:mysql://localhost:3306/lsy?useUnicode" 
            + "=true&characterEncoding=utf-8&useSSL=false&autoReconnect=true";  
	static final String name = "root";
	static final String pass = "123456";
	
	private Connection conn;
	private Statement stmt;
    /**
     * @throws ClassNotFoundException 
     * @throws SQLException 
     * @see HttpServlet#HttpServlet()
     */
    public Sign() throws ClassNotFoundException, SQLException {
        super();
        // TODO Auto-generated constructor stub
        Class.forName(driver);
	   	conn = (Connection) DriverManager.getConnection(url,name,pass);
	   	System.out.println("数据库已连接");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		 try {
		    String username = request.getParameter("username");
		    String password = request.getParameter("password");
	        String name = request.getParameter("name");
	        //name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
	        //name=EncodingUtils.getString(name.toString().getBytes(), "utf-8");
	        System.out.println(name+"2");
		    String age = request.getParameter("age");
		    String telenum = request.getParameter("phone");
		     
			stmt = (Statement) conn.createStatement();
				
			String sql = "SELECT * FROM user where username='"+username+"'";
			ResultSet rs = stmt.executeQuery(sql);
			int size = 0;
			int size1 = 0;
			while(rs.next()) {
				size++;
			}
			sql = "SELECT * FROM person where name='"+name+"'";
			ResultSet rs1 = stmt.executeQuery(sql);
			while(rs1.next()) {
				size1++;
			}
			JSONObject result = new JSONObject();
			if(size != 0)
				result.put("error", "用户名已被注册 ");
			else if(size1!=0) {
				result.put("error", "姓名已被注册 ");
			}
			
			else {
				
				String sql1 = "INSERT INTO user VALUES('"+username+"', '"+password+"')";
				stmt.executeUpdate(sql1);
				String sql2 = "INSERT INTO person VALUES('"+username+"', '"+name+"', "+age+", '"+telenum+"')";
				stmt.executeUpdate(sql2);
				
				
				result = new JSONObject();	
				sql = "SELECT * FROM person where username='"+username+"'";
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					result.put("username", rs.getString("username"));
					result.put("name", rs.getString("name"));
					result.put("age", rs.getInt("age"));
					result.put("phone", rs.getString("phone"));
				}
			}
			
			response.setContentType("text/json;charset=UTF-8");
			
            PrintWriter out = response.getWriter();
			out.println(result.toJSONString());
			rs.close();
			out.close();
			stmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
