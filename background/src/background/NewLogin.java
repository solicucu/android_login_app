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
 * Servlet implementation class NewLogin
 */
@WebServlet("/NewLogin")
public class NewLogin extends HttpServlet {
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
    public NewLogin() throws ClassNotFoundException, SQLException {
        super();
        Class.forName(driver);
	   	conn = (Connection) DriverManager.getConnection(url,name,pass);
	   	System.out.println("数据库已连接");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("后台正在处理");
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			System.out.println(username);
			System.out.println(password);
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT * FROM user where username='"+username+"'";
			ResultSet rs = stmt.executeQuery(sql);
			boolean success = false;
			int count = 0;
			while(rs.next()) {
				if(password.equals(rs.getString("password")))
					success = true;
				count++;
			}
			System.out.print(success);
			JSONObject result = new JSONObject();
			if(success) {
				rs.close();
				sql = "SELECT * FROM person where username='"+username+"'";
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					result.put("username", rs.getString("username"));
					result.put("name", rs.getString("name"));
					result.put("age", rs.getInt("age"));
					result.put("phone", rs.getString("phone"));
				}
			} else {
				if(count == 0) {
					result.put("error", "用户不存在");
				} else {
					result.put("error", "密码错误");
				}
			}
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println(result.toJSONString());
			rs.close();
			out.close();
			stmt.close();
		}
		catch (SQLException e) {
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
