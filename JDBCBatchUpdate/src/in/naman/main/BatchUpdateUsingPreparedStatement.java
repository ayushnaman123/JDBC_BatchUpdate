package in.naman.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import in.naman.util.JdbcUtil;

public class BatchUpdateUsingPreparedStatement {

	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		Scanner scanner = null;
		try {
			connection = JdbcUtil.getJdbcConnection();

			String sqlInsertQuery = "Insert into employees(`name`,`age`,`address`) values(?,?,?)";
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlInsertQuery);
			}

			if (pstmt != null) {
				scanner = new Scanner(System.in);

				if (scanner != null) {
					while (true) {
						System.out.print("Enter the name:: ");
						String name = scanner.next();

						System.out.print("Enter the age:: ");
						int age = scanner.nextInt();

						System.out.print("Enter the address:: ");
						String address = scanner.next();

						pstmt.setString(1, name);
						pstmt.setInt(2, age);
						pstmt.setString(3, address);

						pstmt.addBatch();

						System.out.print("Do you want to add one more record[Yes, No]");
						String option = scanner.next();

						if (option.equalsIgnoreCase("No"))
							break;
					}
					pstmt.executeBatch();
					System.out.println("Record Inserted Successfully......");
				}
			}
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JdbcUtil.cleanUp(connection, pstmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			scanner.close();
		}

	}

}
