package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.MysqlConnect;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MysqlConnect c = MysqlConnect.getDbCon();
		ResultSet rs = null;
		
		if (request.getParameter("registro") != null) {
			String user= request.getParameter("user");
			String pwd= request.getParameter("password");
			String nombre= request.getParameter("nombre");
			String apellidos= request.getParameter("apellidos");
			String email= request.getParameter("email");
			
			try {
				c.insert("INSERT INTO cliente (usuario,password,nombre,apellidos,email) VALUES "
						+ "('"+user+"','"+pwd +"','"+nombre+"','"+apellidos+"','"+email+"')");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			
			request.setAttribute("nombre_user", user);
			
			request.getRequestDispatcher("jsp/menu.jsp").forward(request, response);
					
		} else if (request.getParameter("login") != null) {
			
			String user= request.getParameter("user");
			String pwd= request.getParameter("password");
			
			try {
				rs = c.query("select usuario,password from cliente where usuario='"+user+"' and password='"+pwd+"';");
				if(rs.next()){
					request.getSession().setAttribute("dato", user);
					request.setAttribute("nombre_user", user);
					request.getRequestDispatcher("jsp/tienda.jsp").forward(request,response);
				}else{
					response.sendRedirect("registro.jsp");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
