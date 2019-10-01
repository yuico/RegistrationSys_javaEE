-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.6-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping data for table hotsummer.course: ~3 rows (approximately)
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
REPLACE INTO `course` (`courseID`, `name`, `startTime`) VALUES
	(1, 'Swimming', '09:00:00'),
	(2, 'Tennis', '11:00:00'),
	(3, 'Soccer', '13:00:00');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;

-- Dumping data for table hotsummer.student: ~7 rows (approximately)
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
REPLACE INTO `student` (`studentID`, `firstName`, `lastName`, `age`) VALUES
	(1, 'John', 'Johnson', 10),
	(2, 'Bob', 'Bobson', 11),
	(3, 'Maddie', 'Maddison', 15),
	(4, 'Mary', 'Molson', 12),
	(5, 'Ed', 'Edison', 13),
	(6, 'Mike', 'Molson', 11),
	(7, 'yui', 'kim', 30);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;

-- Dumping data for table hotsummer.studentcourse: ~15 rows (approximately)
/*!40000 ALTER TABLE `studentcourse` DISABLE KEYS */;
REPLACE INTO `studentcourse` (`StudentID`, `CourseID`) VALUES
	(1, 1),
	(1, 2),
	(1, 3),
	(2, 1),
	(2, 2),
	(3, 1),
	(3, 2),
	(3, 3),
	(4, 1),
	(4, 3),
	(5, 1),
	(5, 3),
	(6, 2),
	(6, 3),
	(7, 1);
/*!40000 ALTER TABLE `studentcourse` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
