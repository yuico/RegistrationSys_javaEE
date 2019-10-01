package assignment1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class HotSummer_DBConnector {
	
	static Connection con = null;
	static PreparedStatement ps = null;
	static ResultSet rs = null;
	static String sql = "";
	
	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			
			connect();
			
			String ans;
			
			do {
				System.out.println(
						"\n1 - Add a course\n" +
						"2 - Add a student\n" +
						"3 - Enroll a student in a course\n" +
						"4 - Unenroll a student from a course\n" +
						"5 - Remove a course\n" +
						"6 - List courses given a student first name and last name\n" +
						"7 - List students given a course name\n" +
						"0 - Exit the menu"
						);
				
				ans = input.next();
							
				while(isNaN(ans)) {
					System.out.println("Please enter between 0 and 7.");
					
					ans = input.nextLine();
				}
				
				switch (Integer.parseInt(ans)) {
				
					case 1:
						addCourse();
						break;
						
					case 2:
						addStudent();
						break;
						
					case 3:
						enrollCourse();
						break;
						
					case 4:
						unenrollCourse();
						break;
						
					case 5:
						removeCourse();
						break;
						
					case 6:
						listCourse();
						break;
						
					case 7:
						listStudents();
						break;
						
				}
				
			}while(Integer.parseInt(ans) != 0);
			
			System.out.println("The application closed");
			dbClose();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void connect() throws SQLException {
		String dbName = "hotSummer";
		String dbUrl = "jdbc:mysql://localhost:3306/";
		String id = "root";
		String pw = "";
		
		con = DriverManager.getConnection(dbUrl + dbName, id, pw);
	}
	public static void dbClose() throws SQLException {	
		con.close();
		ps.close();
		rs.close();
	}
	public static void addCourse() throws SQLException {
		String sql = "INSERT INTO Course (name, startTime) VALUES (?,?)";
		
		ps = con.prepareStatement(sql);
		
		System.out.println("Enter the course name.");
		input.nextLine();
		String name = input.nextLine();
		
		
		System.out.println("Enter the course starting time.");
		String tem = input.nextLine();
		String startTime = (Integer.parseInt(tem) < 10) ? "0" + tem + ":00:00" : tem + ":00:00"; 
		ps.setString(1, name);
		ps.setString(2, startTime);
		int num = ps.executeUpdate();
		if(num > 0) {
			System.out.println( name + " has Updated.");
		} else{
			System.out.println("Failed.");		
		}
	}
	
	public static void addStudent() throws SQLException {
		String sql = "INSERT INTO Student (firstName, lastName, age) VALUES (?, ?, ?)";
		
		ps = con.prepareStatement(sql);
		System.out.println("Enter the first name.");
		input.nextLine();
		String firstName = input.nextLine();
		System.out.println("Enter the last name.");
		String lastName = input.nextLine();
		System.out.println("Enter the age.");
		int age = input.nextInt();
		
		ps.setString(1, firstName);
		ps.setString(2, lastName);
		ps.setInt(3, age);
		int num = ps.executeUpdate();
		if(num > 0) {
			System.out.println(firstName + " " + lastName + " has been added." );
		} else {
			System.out.println("Falied.");
		}
	}
	
	public static void enrollCourse() throws SQLException {	
		String sql = "SELECT studentID, firstName, lastName FROM student";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery(sql);
		System.out.println("Please choose the student that you want to enroll the class.");
		while(rs.next()) {
			String stuInfo = rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3);
			System.out.println(stuInfo);
		}
		System.out.println("Enter the student ID.");
		int stuID = input.nextInt();
		
		sql = "SELECT studentId FROM student WHERE studentID = " + stuID;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery(sql);
		if(!rs.next()) {
			System.out.println("Please enter the valid student ID.");
			enrollCourse();
		}
		
		sql = "SELECT courseID, name FROM course";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery(sql);
		System.out.println("Enter the valid course.");
		while(rs.next()) {
			String courseInfo = rs.getString(1) + " " + rs.getString(2);
			System.out.println(courseInfo);
		}		
		System.out.println("Enter the course ID.");
		int courseID = input.nextInt();
		sql = "SELECT courseID FROM course WHERE courseID = " + courseID;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery(sql);
		
		if(!rs.next()) {
			System.out.println("Please enter the valid course ID.");
			enrollCourse();
		}
		
		sql = "INSERT INTO studentcourse (studentID, courseID) VALUES (" 
				+ stuID + ", " + courseID + " )";
		ps = con.prepareStatement(sql);
		int num = ps.executeUpdate();
		if (num > 0) {
			System.out.println("Added.");
		}else { 
			System.out.println("Failed."); 
		}	
	}
	
	public static void unenrollCourse() throws SQLException {	
		String sql = "SELECT studentId, firstName, lastName FROM student";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		System.out.println("Please choose the student that you want to unenroll the class.");
			while(rs.next()) {
				String stuInfo = rs.getString(1)+ " " + rs.getString(2) + " " + rs.getString(3);
				System.out.println(stuInfo);
			}
		
		System.out.println("Enter the student ID.");
		String stuID = input.next();
		while (isNaN(stuID)) {
			System.out.println("Student ID must be a numeric value."
					+ "\nEnter Student ID: ");
			stuID = input.next();
		}
		
		sql = "SELECT studentID FROM student WHERE studentID=" + stuID;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		if(!rs.next()) {
			System.out.println("This Student ID doesn't exist"
					+ "\nPlease Enter the valid student ID.");
			unenrollCourse();
		}
	
		sql = "SELECT c.courseID, c.NAME \r" + 
				"FROM course c\r" + 
				"JOIN studentcourse sc ON sc.courseID=c.courseID\r" + 
				"WHERE studentID=" + stuID;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		
		if(!rs.next()) {
			System.out.println("Student is not enrolled in any courses.");
		}
		System.out.println("Student enrolled in: ");
		while(rs.next()) {
			String info = rs.getString(1)+ " " + rs.getString(2);
			System.out.println(info);
		}
		
		System.out.println("Enter Course ID.");
		String courseID = input.next();
		while(isNaN(courseID)) {
			System.out.println("Course ID must be a numeric value"
					+ "\nEnter Course ID: ");
			courseID = input.next();
		}		
		
		sql = "SELECT courseID FROM course WHERE courseID=" + courseID;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		
		if(!rs.next()) {
		System.out.println("Please Enter Valid Course ID: ");
		unenrollCourse();
		}
		
		sql = "DELETE FROM StudentCourse WHERE studentID=" + stuID 
				+ " AND courseID = " + courseID;
		ps = con.prepareStatement(sql);
		int num = ps.executeUpdate();
		if (num > 0) {
			System.out.println("Unenrolled");
		} else {
			System.out.println("Failed");
		}	
	}
	public static void removeCourse() throws SQLException {	
		String sql = "SELECT courseID, name FROM course";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		System.out.println("Available course:");
		while(rs.next()) {
			String courseInfo = rs.getString(1) + " " + rs.getString(2);
			System.out.println(courseInfo);
		}
		
		System.out.println("Enter the coures ID.");
		String courseID = input.next();
		while(isNaN(courseID)) {
			System.out.println("Course ID must be a numeric value"
					+ "\nEnter Course ID: ");
			courseID = input.next();
		}
		
		sql = "SELECT courseID FROM course WHERE courseID = " + courseID;
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
			
		if(!rs.next()) {
			System.out.println("Please Enter valid course ID.");
			removeCourse();
		}
		
		try {
			sql = "DELETE FROM course\r" + 
				"WHERE courseID=" + courseID + " AND EXISTS (\r" + 
				"SELECT studentID\r" + 
				"FROM studentcourse)";
			ps = con.prepareStatement(sql);
			int num = ps.executeUpdate();
			if(num > 0) {
				System.out.println("Removed"); 
				}
			} catch(SQLException e) {
				System.out.println("Students are taking this course, it cannot be removed.");
			}
	}
	public static void listCourse() throws SQLException {
		
		System.out.println("Enter the First name of the student.");
		input.nextLine();
		String firstName = input.nextLine();
		System.out.println("Enter the last name of the student.");
		String lastName = input.nextLine();
		
		sql = "SELECT firstName, lastName FROM student"
			 + " WHERE UPPER(firstName) LIKE UPPER('%" + firstName + "%')"
			 + " AND UPPER(lastName) LIKE UPPER('%" + lastName + "%')";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		
		if(!rs.next()) {
			System.out.println("Student doesn't exist.");
		}
		
		sql = "SELECT DISTINCT c.name FROM course c \r" + 
			  "JOIN studentCourse sc ON c.courseID = sc.courseID\r" + 
			  "JOIN student s ON s.studentID = sc.studentID\r\n" + 
			  "WHERE UPPER(s.firstName) LIKE UPPER('%" + firstName + "%') " + 
			  "AND UPPER(s.lastName) LIKE UPPER('%" + lastName + "%')";
		ps = con.prepareStatement(sql);		
		rs = ps.executeQuery();
		
		if(rs.next()) {
			System.out.println(firstName + " " + lastName + " is currently enrolled in: ");
			while(rs.next()) {
				System.out.println("\n" + rs.getString("c.name"));
			}
		}else {
			System.out.println(firstName + " " + lastName + " is not taking any course.");
		}
		
	}
	public static void listStudents() throws SQLException {	
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the name of the Course.");
		String courseName = input.nextLine();
		
		sql = "SELECT name FROM course"
				+ "\nWHERE UPPER(name) LIKE UPPER('%" + courseName +"%')";
		ps = con.prepareStatement(sql);		
		rs = ps.executeQuery();
		
		if(!rs.next()) {
			System.out.println(courseName + " course doesn't exist.");
		}
		
		sql = "SELECT DISTINCT s.firstName, s.lastName \r" + 
				"FROM student s \r" + 
				"JOIN studentCourse sc ON s.studentID = sc.studentID\r" + 
				"JOIN course c ON c.courseID = sc.courseID\r" + 
				"WHERE UPPER(c.name) LIKE UPPER('%" + courseName +"%')";
		ps = con.prepareStatement(sql);		
		rs = ps.executeQuery();

		if(rs.next()) {
			System.out.println("The following are the students who take the course.");
			while(rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2));
			}
		} else {
			System.out.println("There is no student attending for this course.");
		}
	}
	
	public static boolean isNaN(String ans) {
	    try {
	    } catch (NumberFormatException | NullPointerException e) {
	        return true;
	    }
	    return false;
	    
	} //end isNan 
	
} //end application