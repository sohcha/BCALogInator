package loginator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

public class DB {
	private Connection conn = null;
	private static DB db = new DB();

	private DB() {
		try (InputStream input = new FileInputStream("config.properties")) {
			Properties prop = new Properties();

			prop.load(input);

			Properties connectionProps = new Properties();
			connectionProps.put("user", prop.getProperty("db.user"));
			connectionProps.put("password", prop.getProperty("db.password"));

			String serverName = prop.getProperty("db.host");
			String port = prop.getProperty("db.port");
			String db = prop.getProperty("db.instance");

			conn = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + port + "/" + db, connectionProps);

			System.out.println("Connected to database");
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	public static ArrayList<Action> loadActions() {
		ArrayList<Action> list = new ArrayList<>();
		String queryString = " select action.action_id, action.student_id, action_type_id, action_dt_time, student_first_name, student_last_name " +
				" from action  " +
				" join student on action.student_id = student.student_id" +
				" order by action_dt_time DESC ";

		try (
				PreparedStatement queryStmt = db.conn.prepareStatement(queryString);
				ResultSet rs = queryStmt.executeQuery();) {

			while (rs.next()) {
				int actionId = rs.getInt("action_id");
				String studentID = rs.getString("student_id");
				String actionType = rs.getString("action_type_id");
				String actionDateTime = rs.getString("action_dt_time");
				String studentFirst = rs.getString("student_first_name");
				String studentLast = rs.getString("student_last_name");
				String studentName = studentFirst + " " + studentLast;

				Action action = new Action(actionId, studentID, actionType, actionDateTime, studentName);

				list.add(action);
			}

		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}

		return list;
	}


	public static Student loadStudent(String studentId) {
		String queryString = " select student.student_id, student_first_name, student_last_name " +
				" from student  " +
				" where student_id = ? ";

		try (
				PreparedStatement queryStmt = db.conn.prepareStatement(queryString)) {
			queryStmt.setString(1, studentId);

			try (ResultSet rs = queryStmt.executeQuery()) {

				if (rs.next()) {
					int studentIdInt = rs.getInt("student_id");
					String studentFirstName = rs.getString("student_first_name");
					String studentLastName = rs.getString("student_last_name");

					return new Student(studentIdInt, studentFirstName, studentLastName);
				}
			}
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}

		return null;
	}

	public static void recordAction(String studentId, String actionType) {
		String query = "insert into action(student_id, action_type_id, action_dt_time) values (?,?,CONVERT_TZ(NOW(),'SYSTEM','America/Montreal')) ";
		try (PreparedStatement insertStmt = db.conn.prepareStatement(query)) {
			insertStmt.setString(1, studentId);
			insertStmt.setString(2, actionType); 

			insertStmt.executeUpdate();
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
		
		
	}

	public static ArrayList<String[]> loadSignedInStudentsToday() {
		ArrayList<String[]> list = new ArrayList<>();
		String queryString = "SELECT s.student_id, s.student_first_name, s.student_last_name, MIN(a.action_dt_time) AS earliest_sign_in " + // Check if we want earliest or latest
							 "FROM action a " +
							 "JOIN student s ON a.student_id = s.student_id " +
							 "WHERE a.action_type_id = 'SIGNED_IN' AND DATE(a.action_dt_time) = CURDATE() " +
							 "GROUP BY s.student_id, s.student_first_name, s.student_last_name";
	
		try (PreparedStatement queryStmt = db.conn.prepareStatement(queryString);
			 ResultSet rs = queryStmt.executeQuery()) {
	
			while (rs.next()) {
				String studentID = rs.getString("student_id");
				String firstName = rs.getString("student_first_name");
				String lastName = rs.getString("student_last_name");
				String signInDateTime = rs.getString("earliest_sign_in");
	
				list.add(new String[] { studentID, firstName + " " + lastName, signInDateTime });
			}
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	
		return list;
	}

	public static void clearAllActions() {
		String query = "DELETE FROM action";
		try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
			stmt.executeUpdate();
			System.out.println("All actions cleared from the database.");
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}
}
