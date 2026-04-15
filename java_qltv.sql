-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: java_qltv
-- ------------------------------------------------------
-- Server version	8.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `ma_admin` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `ho_ten` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `gioi_tinh` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `sdt` varchar(15) COLLATE utf8mb4_general_ci NOT NULL,
  `id` int NOT NULL,
  PRIMARY KEY (`ma_admin`),
  KEY `id` (`id`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('ad1','a đờ min','nu','09768445',1);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hinh_phat`
--

DROP TABLE IF EXISTS `hinh_phat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hinh_phat` (
  `ma_hp` int NOT NULL AUTO_INCREMENT,
  `ma_sv` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ly_do` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `ngay_phat` date NOT NULL,
  `hinh_thuc` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `tien_do` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ma_pm` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`ma_hp`),
  KEY `ma_sv` (`ma_sv`),
  KEY `ma_pm` (`ma_pm`),
  CONSTRAINT `hinh_phat_ibfk_1` FOREIGN KEY (`ma_sv`) REFERENCES `sinh_vien` (`ma_sv`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hinh_phat_ibfk_2` FOREIGN KEY (`ma_pm`) REFERENCES `phieu_muon` (`ma_pm`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hinh_phat`
--

LOCK TABLES `hinh_phat` WRITE;
/*!40000 ALTER TABLE `hinh_phat` DISABLE KEYS */;
/*!40000 ALTER TABLE `hinh_phat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loai_sach`
--

DROP TABLE IF EXISTS `loai_sach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loai_sach` (
  `ma_loai_sach` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ten_loai_sach` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`ma_loai_sach`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loai_sach`
--

LOCK TABLES `loai_sach` WRITE;
/*!40000 ALTER TABLE `loai_sach` DISABLE KEYS */;
INSERT INTO `loai_sach` VALUES ('LS01','Công nghệ thông tin'),('LS02','Nghệ thuật – Giải trí'),('LS03','Thiếu nhi'),('LS04','Lịch sử – văn hóa'),('LS05','Văn học');
/*!40000 ALTER TABLE `loai_sach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieu_muon`
--

DROP TABLE IF EXISTS `phieu_muon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieu_muon` (
  `ma_pm` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ma_sv` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ma_sach` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `tinh_trang` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ngay_muon` date NOT NULL,
  `ngay_tra` date NOT NULL,
  `so_luong` int NOT NULL,
  PRIMARY KEY (`ma_pm`),
  KEY `ma_sach` (`ma_sach`),
  KEY `ma_sv` (`ma_sv`),
  CONSTRAINT `phieu_muon_ibfk_1` FOREIGN KEY (`ma_sach`) REFERENCES `sach` (`ma_sach`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `phieu_muon_ibfk_2` FOREIGN KEY (`ma_sv`) REFERENCES `sinh_vien` (`ma_sv`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieu_muon`
--

LOCK TABLES `phieu_muon` WRITE;
/*!40000 ALTER TABLE `phieu_muon` DISABLE KEYS */;
/*!40000 ALTER TABLE `phieu_muon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sach`
--

DROP TABLE IF EXISTS `sach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sach` (
  `ma_sach` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ten_sach` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `ma_loai_sach` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ma_tg` int NOT NULL,
  `nha_xb` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `nam_xb` int NOT NULL,
  `so_luong` int NOT NULL,
  `mo_ta` varchar(500) COLLATE utf8mb4_general_ci NOT NULL,
  `image` varchar(300) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`ma_sach`),
  KEY `ma_loai_sach` (`ma_loai_sach`),
  KEY `sach_ibfk_2` (`ma_tg`),
  CONSTRAINT `sach_ibfk_1` FOREIGN KEY (`ma_loai_sach`) REFERENCES `loai_sach` (`ma_loai_sach`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sach_ibfk_2` FOREIGN KEY (`ma_tg`) REFERENCES `tac_gia` (`ma_tg`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sach`
--

LOCK TABLES `sach` WRITE;
/*!40000 ALTER TABLE `sach` DISABLE KEYS */;
INSERT INTO `sach` VALUES ('S001','Lập trình Java cơ bản','LS01',4,'NXB Thế Giới',2018,50,'Giới thiệu cú pháp Java, lập trình hướng đối tượng, các ví dụ thực hành để học sinh và sinh viên nắm vững kỹ năng lập trình cơ bản.','laptrinhjava.jpg'),('S0010','Vợ chồng A Phủ','LS05',2,'Nhà xuất bản Kim Đồng',1952,0,'Truyện kể về Mị, một cô gái Mông xinh đẹp, hiếu thảo nhưng bị bắt về làm dâu gạt nợ cho nhà thống lí Pá Tra, sống kiếp nô lệ tăm tối. A Phủ là chàng trai khỏe mạnh, gan góc, cũng bị áp bức và trở thành nô lệ trong nhà thống lí. Khi A Phủ bị trói chờ chết, Mị đã cắt dây cứu anh. Họ cùng nhau trốn đi, đến Phiềng Sa và trở thành vợ chồng, tham gia cách mạng.','aphu.jpg'),('S002','C: The Complete Reference','LS01',4,'McGraw-Hill',1987,1,'Sách tham khảo toàn diện về ngôn ngữ C, trình bày cú pháp, thư viện chuẩn và ví dụ minh họa rõ ràng. Phù hợp cho người học và lập trình viên trung cấp.','c.jpg'),('S003','C#: The Complete Reference','LS01',4,'McGraw-Hill',2002,0,'Trình bày toàn diện về C# và nền tảng .NET, từ cơ bản đến nâng cao, phù hợp cho lập trình ứng dụng Windows và web.','Csharp.jpg'),('S004','C++: The Complete Reference','LS01',4,'McGraw-Hill',1991,1,'Giới thiệu đầy đủ về C++, từ lập trình hướng đối tượng đến các tính năng nâng cao. Là một trong những sách C++ phổ biến nhất thời kỳ đầu.','c++.jpg'),('S005','Lịch sử mỹ thuật thế giới','LS02',5,'NXB Thế Giới',2015,0,'Giới thiệu các trường phái mỹ thuật, danh họa nổi tiếng, tác phẩm tiêu biểu, phù hợp cho sinh viên mỹ thuật và người yêu hội họa.','lichsumithuattg.jpg'),('S006','Hồ Chí Minh - Toàn Tập','LS04',3,'Nhà xuất bản Chính trị quốc gia - Sự thật',1995,4,'Hồ Chí Minh Toàn tập là bộ sách tập hợp đầy đủ các tác phẩm của Chủ tịch Hồ Chí Minh.\r\nNội dung: Gồm các bài viết, bài nói, thư từ, điện, lời kêu gọi, báo cáo, tác phẩm lý luận – chính trị – văn hóa của Hồ Chí Minh từ năm 1912 đến 1969.\r\n\r\nGiá trị: Là nguồn tư liệu quan trọng để nghiên cứu tư tưởng, đạo đức, phong cách Hồ Chí Minh và lịch sử cách mạng Việt Nam.','toantap.jpg'),('S007','Chí Phèo','LS05',1,'Nhà xuất bản Văn Học',1941,15,'Truyện ngắn “Chí Phèo” của Nam Cao kể về cuộc đời bi kịch của Chí Phèo – một người nông dân lương thiện nhưng bị xã hội phong kiến đẩy vào con đường tha hóa. Từ một người hiền lành, Chí Phèo bị tù tội, trở nên nghiện rượu, hung bạo và bị dân làng kỳ thị. Dù trong sâu thẳm, anh vẫn khao khát được sống như người lương thiện và được yêu thương, đặc biệt là qua mối tình với Thị Nở, nhưng định kiến xã hội đã ngăn cản anh. Cuộc đời Chí Phèo là hình ảnh bi thương của những con người bị xã hội đẩy ra bê','chipheo.jpg'),('S008','Lão Hạc','LS05',1,'Nhà xuất bản Văn Học',1943,16,'Truyện ngắn “Lão Hạc” của Nam Cao kể về cuộc đời bi thương của lão Hạc – một người nông dân nghèo sống một mình trong cảnh cô đơn và túng thiếu. Lão Hạc yêu thương con chó vàng như người bạn duy nhất, nhưng vì hoàn cảnh khó khăn, ông phải bán nó để lấy tiền sinh sống, lòng đau xót nhưng vẫn thực hiện. Cuối cùng, để giữ lại chút tự trọng và không muốn làm khổ con trai, lão Hạc chọn cách kết thúc cuộc đời mình. Truyện phản ánh hiện thực khắc nghiệt của xã hội phong kiến, đồng thời khắc họa tấm lòn','laohac.jpg'),('S009','Đôi mắt','LS05',1,'Nhà xuất bản Văn Học',1942,20,'ruyện xoay quanh cuộc gặp gỡ và tranh luận giữa hai nhân vật Hoàng và Độ. Qua cách nhìn nhận con người và cuộc sống của họ, Nam Cao đặt ra vấn đề: người trí thức phải thay đổi cách nhìn, phải biết tin tưởng, gắn bó và sống cùng nhân dân trong kháng chiến. “Đôi mắt” trở thành biểu tượng cho cách nhìn đời, nhìn người: một bên là cái nhìn phiến diện, xa rời quần chúng; bên kia là cái nhìn tiến bộ, giàu trách nhiệm xã hội.','doimat.jpg'),('S011','Xóm giếng','LS05',1,'Nhà xuất bản Kim Đồng',1941,20,'ruyện lấy bối cảnh một xóm nghèo quanh giếng nước, nơi tập trung những con người lam lũ, sống lay lắt qua ngày. Cuộc sống túng thiếu, tù túng khiến con người trở nên mệt mỏi, ích kỷ và dễ nảy sinh mâu thuẫn. Qua những sinh hoạt đời thường và số phận nhỏ bé của người dân xóm giếng, Nam Cao phơi bày hiện thực tàn nhẫn của xã hội, nơi cái nghèo và sự thờ ơ làm con người dần mất đi niềm tin và tình người.','xomgieng.jpg'),('S012','Dế Mèn phiêu lưu ','LS03',2,'Nhà xuất bản Đời Nay',1941,0,'Truyện “Dế Mèn phiêu lưu ký” của Tô Hoài kể về cuộc phiêu lưu của chú Dế Mèn, từ một chú dế kiêu ngạo, háo thắng đến khi trưởng thành và học được bài học về tình bạn, lòng dũng cảm và sự khiêm tốn. Trong hành trình của mình, Dế Mèn gặp nhiều thử thách và nguy hiểm, đồng thời kết bạn với các loài côn trùng khác, từ đó nhận ra giá trị của sự sẻ chia, trách nhiệm và nhận lỗi. Truyện không chỉ mang tính giải trí mà còn giáo dục, giúp trẻ em hiểu về đạo đức, nhân cách và cách ứng xử trong cuộc sống.','demen.jpg');
/*!40000 ALTER TABLE `sach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sinh_vien`
--

DROP TABLE IF EXISTS `sinh_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sinh_vien` (
  `ma_sv` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ho_ten` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `lop` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `gioi_tinh` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `ngay_sinh` date NOT NULL,
  `dia_chi` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `sdt` varchar(15) COLLATE utf8mb4_general_ci NOT NULL,
  `id` int NOT NULL,
  PRIMARY KEY (`ma_sv`),
  KEY `id` (`id`),
  CONSTRAINT `sinh_vien_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sinh_vien`
--

LOCK TABLES `sinh_vien` WRITE;
/*!40000 ALTER TABLE `sinh_vien` DISABLE KEYS */;
INSERT INTO `sinh_vien` VALUES ('B23DCKD014','B23DCKD014','Chưa cập nhật','Nam','2000-01-01','Chưa cập nhật','0000000000',10),('B23DCKD032','B23DCKD032','Chưa cập nhật','N/A','2000-01-01','Chưa cập nhật','0000000000',9);
/*!40000 ALTER TABLE `sinh_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tac_gia`
--

DROP TABLE IF EXISTS `tac_gia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tac_gia` (
  `ma_tg` int NOT NULL AUTO_INCREMENT,
  `ten_tg` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ngay_sinh` date NOT NULL,
  `gioi_tinh` varchar(5) COLLATE utf8mb4_general_ci NOT NULL,
  `que` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `tieu_su` text COLLATE utf8mb4_general_ci NOT NULL,
  `hinh` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`ma_tg`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tac_gia`
--

LOCK TABLES `tac_gia` WRITE;
/*!40000 ALTER TABLE `tac_gia` DISABLE KEYS */;
INSERT INTO `tac_gia` VALUES (1,'Nam Cao','1915-10-29','Nam','Hòa Hậu, Lý Nhân, Hà Nam','Nam Cao (1917–1951) là nhà văn hiện thực xuất sắc của văn học Việt Nam giai đoạn trước và sau Cách mạng Tháng Tám. Ông nổi tiếng với các tác phẩm viết về số phận người nông dân và trí thức nghèo, tiêu biểu như *Chí Phèo*, *Lão Hạc*, *Đời thừa*. Văn chương Nam Cao giàu tính nhân đạo, phân tích sâu tâm lý con người. Ông hy sinh trong kháng chiến chống Pháp năm 1951.\r\n','namcao.jpg'),(2,'Tô Hoài','1920-09-27','Nam','Thanh Oai, Hà Nội','Tô Hoài (1920–2014) là nhà văn lớn của văn học Việt Nam hiện đại. Ông nổi tiếng với lối viết gần gũi, sinh động, am hiểu đời sống sinh hoạt và phong tục, đặc biệt là miền núi Tây Bắc. Tác phẩm tiêu biểu: *Dế Mèn phiêu lưu ký, Vợ chồng A Phủ. Tô Hoài có sự nghiệp sáng tác đồ sộ và đóng góp quan trọng cho văn học thiếu nhi và văn xuôi Việt Nam.\r\n','tohoai.jpg'),(3,'Hồ Chí Minh','1890-05-19','Nam','Kim Liên, Nam Đàn, Nghệ An','Hồ Chí Minh (1890–1969) là lãnh tụ vĩ đại của dân tộc Việt Nam, Chủ tịch nước Việt Nam Dân chủ Cộng hòa. Người là nhà cách mạng, nhà tư tưởng, nhà văn hóa lớn, sáng lập và lãnh đạo Đảng Cộng sản Việt Nam. Hồ Chí Minh dẫn dắt nhân dân Việt Nam đấu tranh giành độc lập, tiêu biểu với Tuyên ngôn Độc lập (1945). Cuộc đời và sự nghiệp của Người gắn liền với sự nghiệp giải phóng dân tộc và xây dựng đất nước.\r\n','hochiminh.jpg'),(4,'Herbert Schildt','1951-02-28','Nam','Chicago, Illinois, Hoa Kỳ','Herbert Schildt là nhà văn và lập trình viên người Mỹ, nổi tiếng với các cuốn sách về ngôn ngữ lập trình, đặc biệt là **C, C++, Java và C#**. Ông là tác giả của nhiều sách học lập trình phổ biến dành cho người mới bắt đầu, được xuất bản rộng rãi trên thế giới. Tác phẩm của Herbert Schildt có phong cách trình bày rõ ràng, dễ hiểu, góp phần phổ biến kiến thức lập trình cho nhiều thế hệ người học.\r\n','herbert.jpg'),(5,'Ernst Gombrich','1909-03-30','Nam','Luân Đôn, Vương Quốc Anh','Ernst Gombrich (1909–2001) là sử gia và nhà phê bình nghệ thuật người Áo–Anh. Ông nổi tiếng toàn cầu với cuốn Câu chuyện nghệ thuật (The Story of Art), một trong những sách nhập môn về lịch sử nghệ thuật có ảnh hưởng lớn nhất thế kỷ 20. Các công trình của Gombrich đề cao cách tiếp cận nghệ thuật gắn với nhận thức và tâm lý con người.\r\n','ernst.jpg');
/*!40000 ALTER TABLE `tac_gia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `role` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ad1','12345','admin@gmail.com',0),(9,'B23DCKD032','$2a$12$nbh5kLqVXitPQhfKNILDu.NrbR4yIJkefDJJbeIHEqRE9msyVyXSS','hihi123@gmail.com',1),(10,'B23DCKD014','$2a$12$xxECO8tMKuZUuMVyCSl67ezNWVdsg33VxkhPAf7XXBLJ9yhglDAwC','123@gmail.com',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yeu_thich`
--

DROP TABLE IF EXISTS `yeu_thich`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `yeu_thich` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma_sach` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ma_sv` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ma_sach` (`ma_sach`),
  KEY `ma_sv` (`ma_sv`),
  CONSTRAINT `yeu_thich_ibfk_2` FOREIGN KEY (`ma_sach`) REFERENCES `sach` (`ma_sach`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `yeu_thich_ibfk_3` FOREIGN KEY (`ma_sv`) REFERENCES `sinh_vien` (`ma_sv`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=370 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yeu_thich`
--

LOCK TABLES `yeu_thich` WRITE;
/*!40000 ALTER TABLE `yeu_thich` DISABLE KEYS */;
/*!40000 ALTER TABLE `yeu_thich` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-15 17:08:35
