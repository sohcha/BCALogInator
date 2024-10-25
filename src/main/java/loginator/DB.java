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

			// load a properties file
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

	// /** Returns an array list of the leagues in the database. */
	// public static ArrayList<League> loadLeagues() {
	// 	ArrayList<League> list = new ArrayList<>();
	// 	String queryString = " select league.league_id, league_name, count(team.league_id)  as teams_count " +
	// 			" from league  " +
	// 			" left join team on league.league_id = team.league_id " +
	// 			" group by league.league_id, league_name " +
	// 			" order by league_name ";

	// 	try (
	// 			PreparedStatement queryStmt = db.conn.prepareStatement(queryString);
	// 			ResultSet rs = queryStmt.executeQuery();) {

	// 		while (rs.next()) {
	// 			int leagueId = rs.getInt("league_id");
	// 			String leagueName = rs.getString("league_name");

	// 			boolean hasTeams = false;
	// 			if (rs.getInt("teams_count") > 0) {
	// 				hasTeams = true;
	// 			}

	// 			League league = new League(leagueId, leagueName, hasTeams);

	// 			list.add(league);
	// 		}

	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}

	// 	return list;
	// }


	/** Loads a single league given an id. */
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

	// /** Adds a new league to the database. */
	// public static void insertLeague(String leagueName) {
	// 	String query = "insert into league(league_name) values (?)";

	// 	try (PreparedStatement insertStmt = db.conn.prepareStatement(query)) {

	// 		insertStmt.setString(1, leagueName);
	// 		insertStmt.executeUpdate();
	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}
	// }

	// /** Updates the name of a league in the database. */
	// public static void updateLeague(League league) {
	// 	String query = "update league set league_name = ? where league_id = ?";

	// 	try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

	// 		updateStmt.setString(1, league.getLeagueName());
	// 		updateStmt.setInt(2, league.getLeagueId());

	// 		updateStmt.executeUpdate();
	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}
	// }

	// /** Deletes the given league from the database. */
	// public static void deleteLeague(int leagueId) {
	// 	String query = "delete from league where league_id = ?";

	// 	try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

	// 		updateStmt.setInt(1, leagueId);
	// 		updateStmt.executeUpdate();
	// 	} catch (Exception ex) {
	// 		System.err.println(ex);
	// 		ex.printStackTrace(System.err);
	// 	}
	// }

}
