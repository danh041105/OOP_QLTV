-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 07, 2026 lúc 03:34 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `java_qltv`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admin`
--

CREATE TABLE `admin` (
  `ma_admin` varchar(10) NOT NULL,
  `ho_ten` varchar(50) NOT NULL,
  `gioi_tinh` varchar(50) NOT NULL,
  `sdt` varchar(15) NOT NULL,
  `email` varchar(50) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `admin`
--

INSERT INTO `admin` (`ma_admin`, `ho_ten`, `gioi_tinh`, `sdt`, `email`, `id`) VALUES
('ad1', 'a đờ min', 'nu', '09768445', 'admin@gmail.com', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admin_islogin`
--

CREATE TABLE `admin_islogin` (
  `id` int(11) NOT NULL,
  `ma_admin` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `dang_nhap`
--

CREATE TABLE `dang_nhap` (
  `id` int(11) NOT NULL,
  `ma_sv` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hinh_phat`
--

CREATE TABLE `hinh_phat` (
  `ma_hp` int(50) NOT NULL,
  `ma_sv` varchar(50) NOT NULL,
  `ly_do` varchar(200) NOT NULL,
  `ngay_phat` date NOT NULL,
  `hinh_thuc` varchar(200) NOT NULL,
  `tien_do` varchar(50) NOT NULL,
  `ma_pm` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loai_sach`
--

CREATE TABLE `loai_sach` (
  `ma_loai_sach` varchar(50) NOT NULL,
  `ten_loai_sach` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `loai_sach`
--

INSERT INTO `loai_sach` (`ma_loai_sach`, `ten_loai_sach`) VALUES
('LS01', 'Công nghệ thông tin'),
('LS02', 'Nghệ thuật – Giải trí'),
('LS03', 'Thiếu nhi'),
('LS04', 'Lịch sử – văn hóa'),
('LS05', 'Văn học');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieu_muon`
--

CREATE TABLE `phieu_muon` (
  `ma_pm` varchar(50) NOT NULL,
  `ma_sv` varchar(50) NOT NULL,
  `ma_sach` varchar(50) NOT NULL,
  `tinh_trang` varchar(50) NOT NULL,
  `ngay_muon` date NOT NULL,
  `ngay_tra` date NOT NULL,
  `so_luong` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieu_muon`
--

INSERT INTO `phieu_muon` (`ma_pm`, `ma_sv`, `ma_sach`, `tinh_trang`, `ngay_muon`, `ngay_tra`, `so_luong`) VALUES
('PM1767603211029', 'SV01', 'S003', 'Đã trả', '2026-01-05', '2026-01-06', 10),
('PM1767603239020', 'SV01', 'S004', 'Trả chậm', '2026-01-05', '2026-01-11', 2),
('PM1767608487095', 'SV01', 'S003', 'Quá hạn trả', '2026-01-05', '2026-01-06', 10),
('PM1767608909206', 'SV01', 'S003', 'Đang mượn', '2026-01-05', '2026-01-06', 20),
('PM1767609970176', 'SV01', 'S003', 'Đang mượn', '2026-01-05', '2026-01-06', 5),
('PM1767625407373', 'SV015', 'S001', 'Đang mượn', '2026-01-05', '2026-01-11', 6),
('PM1767697280035', 'SV01', 'S006', 'Đang mượn', '2026-01-06', '2026-01-13', 2),
('PM1767697316737', 'SV01', 'S005', 'Đang mượn', '2026-01-06', '2026-01-11', 20),
('PM1767698066499', 'SV01', 'S002', 'Đang mượn', '2026-01-06', '2026-01-13', 5),
('PM1767698362054', 'SV01', 'S003', 'Đang mượn', '2026-01-06', '2026-01-07', 5),
('PM1767716785373', 'SV01', 'S006', 'Đang mượn', '2026-01-06', '2026-01-07', 1),
('PM1767716853666', 'SV01', 'S001', 'Đang mượn', '2026-01-06', '2026-01-08', 4),
('PM1767718321254', 'SV01', 'S002', 'Đang mượn', '2026-01-06', '2026-01-08', 5),
('PM1767718601079', 'SV01', 'S012', 'Đang mượn', '2026-01-06', '2026-01-07', 4),
('PM1767721157855', 'SV01', 'S0010', 'Đang mượn', '2026-01-07', '2026-01-08', 60),
('PM1767743048042', 'SV01', 'S004', 'Đang mượn', '2026-01-07', '2026-01-08', 9),
('PM1767743104106', 'SV015', 'S004', 'Đang mượn', '2026-01-07', '2026-01-09', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sach`
--

CREATE TABLE `sach` (
  `ma_sach` varchar(50) NOT NULL,
  `ten_sach` varchar(100) NOT NULL,
  `ma_loai_sach` varchar(50) NOT NULL,
  `ma_tg` int(11) NOT NULL,
  `nha_xb` varchar(100) NOT NULL,
  `nam_xb` int(5) NOT NULL,
  `so_luong` int(11) NOT NULL,
  `tinh_trang` bit(1) NOT NULL,
  `mo_ta` varchar(500) NOT NULL,
  `image` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sach`
--

INSERT INTO `sach` (`ma_sach`, `ten_sach`, `ma_loai_sach`, `ma_tg`, `nha_xb`, `nam_xb`, `so_luong`, `tinh_trang`, `mo_ta`, `image`) VALUES
('S001', 'Lập trình Java cơ bản', 'LS01', 4, 'NXB Thế Giới', 2018, 50, b'1', 'Giới thiệu cú pháp Java, lập trình hướng đối tượng, các ví dụ thực hành để học sinh và sinh viên nắm vững kỹ năng lập trình cơ bản.', 'laptrinhjava.jpg'),
('S0010', 'Vợ chồng A Phủ', 'LS05', 2, 'Nhà xuất bản Kim Đồng', 1952, 0, b'0', 'Truyện kể về Mị, một cô gái Mông xinh đẹp, hiếu thảo nhưng bị bắt về làm dâu gạt nợ cho nhà thống lí Pá Tra, sống kiếp nô lệ tăm tối. A Phủ là chàng trai khỏe mạnh, gan góc, cũng bị áp bức và trở thành nô lệ trong nhà thống lí. Khi A Phủ bị trói chờ chết, Mị đã cắt dây cứu anh. Họ cùng nhau trốn đi, đến Phiềng Sa và trở thành vợ chồng, tham gia cách mạng.', 'aphu.jpg'),
('S002', 'C: The Complete Reference', 'LS01', 4, 'McGraw-Hill', 1987, 10, b'1', 'Sách tham khảo toàn diện về ngôn ngữ C, trình bày cú pháp, thư viện chuẩn và ví dụ minh họa rõ ràng. Phù hợp cho người học và lập trình viên trung cấp.', 'c.jpg'),
('S003', 'C#: The Complete Reference', 'LS01', 4, 'McGraw-Hill', 2002, 0, b'0', 'Trình bày toàn diện về C# và nền tảng .NET, từ cơ bản đến nâng cao, phù hợp cho lập trình ứng dụng Windows và web.', 'Csharp.jpg'),
('S004', 'C++: The Complete Reference', 'LS01', 4, 'McGraw-Hill', 1991, 4, b'0', 'Giới thiệu đầy đủ về C++, từ lập trình hướng đối tượng đến các tính năng nâng cao. Là một trong những sách C++ phổ biến nhất thời kỳ đầu.', 'c++.jpg'),
('S005', 'Lịch sử mỹ thuật thế giới', 'LS02', 5, 'NXB Thế Giới', 2015, 0, b'0', 'Giới thiệu các trường phái mỹ thuật, danh họa nổi tiếng, tác phẩm tiêu biểu, phù hợp cho sinh viên mỹ thuật và người yêu hội họa.', 'lichsumithuattg.jpg'),
('S006', 'Hồ Chí Minh - Toàn Tập', 'LS04', 3, 'Nhà xuất bản Chính trị quốc gia - Sự thật', 1995, 4, b'1', 'Hồ Chí Minh Toàn tập là bộ sách tập hợp đầy đủ các tác phẩm của Chủ tịch Hồ Chí Minh.\r\nNội dung: Gồm các bài viết, bài nói, thư từ, điện, lời kêu gọi, báo cáo, tác phẩm lý luận – chính trị – văn hóa của Hồ Chí Minh từ năm 1912 đến 1969.\r\n\r\nGiá trị: Là nguồn tư liệu quan trọng để nghiên cứu tư tưởng, đạo đức, phong cách Hồ Chí Minh và lịch sử cách mạng Việt Nam.', 'toantap.jpg'),
('S007', 'Chí Phèo', 'LS05', 1, 'Nhà xuất bản Văn Học', 1941, 15, b'1', 'Truyện ngắn “Chí Phèo” của Nam Cao kể về cuộc đời bi kịch của Chí Phèo – một người nông dân lương thiện nhưng bị xã hội phong kiến đẩy vào con đường tha hóa. Từ một người hiền lành, Chí Phèo bị tù tội, trở nên nghiện rượu, hung bạo và bị dân làng kỳ thị. Dù trong sâu thẳm, anh vẫn khao khát được sống như người lương thiện và được yêu thương, đặc biệt là qua mối tình với Thị Nở, nhưng định kiến xã hội đã ngăn cản anh. Cuộc đời Chí Phèo là hình ảnh bi thương của những con người bị xã hội đẩy ra bê', 'chipheo.jpg'),
('S008', 'Lão Hạc', 'LS05', 1, 'Nhà xuất bản Văn Học', 1943, 16, b'1', 'Truyện ngắn “Lão Hạc” của Nam Cao kể về cuộc đời bi thương của lão Hạc – một người nông dân nghèo sống một mình trong cảnh cô đơn và túng thiếu. Lão Hạc yêu thương con chó vàng như người bạn duy nhất, nhưng vì hoàn cảnh khó khăn, ông phải bán nó để lấy tiền sinh sống, lòng đau xót nhưng vẫn thực hiện. Cuối cùng, để giữ lại chút tự trọng và không muốn làm khổ con trai, lão Hạc chọn cách kết thúc cuộc đời mình. Truyện phản ánh hiện thực khắc nghiệt của xã hội phong kiến, đồng thời khắc họa tấm lòn', 'laohac.jpg'),
('S009', 'Đôi mắt', 'LS05', 1, 'Nhà xuất bản Văn Học', 1942, 20, b'1', 'ruyện xoay quanh cuộc gặp gỡ và tranh luận giữa hai nhân vật Hoàng và Độ. Qua cách nhìn nhận con người và cuộc sống của họ, Nam Cao đặt ra vấn đề: người trí thức phải thay đổi cách nhìn, phải biết tin tưởng, gắn bó và sống cùng nhân dân trong kháng chiến. “Đôi mắt” trở thành biểu tượng cho cách nhìn đời, nhìn người: một bên là cái nhìn phiến diện, xa rời quần chúng; bên kia là cái nhìn tiến bộ, giàu trách nhiệm xã hội.', 'doimat.jpg'),
('S011', 'Xóm giếng', 'LS05', 1, 'Nhà xuất bản Kim Đồng', 1941, 20, b'1', 'ruyện lấy bối cảnh một xóm nghèo quanh giếng nước, nơi tập trung những con người lam lũ, sống lay lắt qua ngày. Cuộc sống túng thiếu, tù túng khiến con người trở nên mệt mỏi, ích kỷ và dễ nảy sinh mâu thuẫn. Qua những sinh hoạt đời thường và số phận nhỏ bé của người dân xóm giếng, Nam Cao phơi bày hiện thực tàn nhẫn của xã hội, nơi cái nghèo và sự thờ ơ làm con người dần mất đi niềm tin và tình người.', 'xomgieng.jpg'),
('S012', 'Dế Mèn phiêu lưu ', 'LS03', 2, 'Nhà xuất bản Đời Nay', 1941, 0, b'0', 'Truyện “Dế Mèn phiêu lưu ký” của Tô Hoài kể về cuộc phiêu lưu của chú Dế Mèn, từ một chú dế kiêu ngạo, háo thắng đến khi trưởng thành và học được bài học về tình bạn, lòng dũng cảm và sự khiêm tốn. Trong hành trình của mình, Dế Mèn gặp nhiều thử thách và nguy hiểm, đồng thời kết bạn với các loài côn trùng khác, từ đó nhận ra giá trị của sự sẻ chia, trách nhiệm và nhận lỗi. Truyện không chỉ mang tính giải trí mà còn giáo dục, giúp trẻ em hiểu về đạo đức, nhân cách và cách ứng xử trong cuộc sống.', 'demen.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sinh_vien`
--

CREATE TABLE `sinh_vien` (
  `ma_sv` varchar(50) NOT NULL,
  `ho_ten` varchar(100) NOT NULL,
  `lop` varchar(50) NOT NULL,
  `gioi_tinh` varchar(10) NOT NULL,
  `ngay_sinh` date NOT NULL,
  `dia_chi` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `sdt` varchar(15) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sinh_vien`
--

INSERT INTO `sinh_vien` (`ma_sv`, `ho_ten`, `lop`, `gioi_tinh`, `ngay_sinh`, `dia_chi`, `email`, `sdt`, `id`) VALUES
('SV01', 'Nguyễn Văn A', 'CNTT1', 'Nam', '2003-08-15', 'Hà Nội', 'a@gmail.com', '0123456789', 2),
('SV015', 'Lê Văn C', 'TT24', 'Nam', '0013-02-25', 'Hà Nội', 'phuonglinh9005@gmail.com', '0123456789', 6);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tac_gia`
--

CREATE TABLE `tac_gia` (
  `ma_tg` int(11) NOT NULL,
  `ten_tg` varchar(50) NOT NULL,
  `ngay_sinh` date NOT NULL,
  `gioi_tinh` varchar(5) NOT NULL,
  `que` varchar(100) NOT NULL,
  `tieu_su` text NOT NULL,
  `hinh` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tac_gia`
--

INSERT INTO `tac_gia` (`ma_tg`, `ten_tg`, `ngay_sinh`, `gioi_tinh`, `que`, `tieu_su`, `hinh`) VALUES
(1, 'Nam Cao', '1915-10-29', 'Nam', 'Hòa Hậu, Lý Nhân, Hà Nam', 'Nam Cao (1917–1951) là nhà văn hiện thực xuất sắc của văn học Việt Nam giai đoạn trước và sau Cách mạng Tháng Tám. Ông nổi tiếng với các tác phẩm viết về số phận người nông dân và trí thức nghèo, tiêu biểu như *Chí Phèo*, *Lão Hạc*, *Đời thừa*. Văn chương Nam Cao giàu tính nhân đạo, phân tích sâu tâm lý con người. Ông hy sinh trong kháng chiến chống Pháp năm 1951.\r\n', 'namcao.jpg'),
(2, 'Tô Hoài', '1920-09-27', 'Nam', 'Thanh Oai, Hà Nội', 'Tô Hoài (1920–2014) là nhà văn lớn của văn học Việt Nam hiện đại. Ông nổi tiếng với lối viết gần gũi, sinh động, am hiểu đời sống sinh hoạt và phong tục, đặc biệt là miền núi Tây Bắc. Tác phẩm tiêu biểu: *Dế Mèn phiêu lưu ký, Vợ chồng A Phủ. Tô Hoài có sự nghiệp sáng tác đồ sộ và đóng góp quan trọng cho văn học thiếu nhi và văn xuôi Việt Nam.\r\n', 'tohoai.jpg'),
(3, 'Hồ Chí Minh', '1890-05-19', 'Nam', 'Kim Liên, Nam Đàn, Nghệ An', 'Hồ Chí Minh (1890–1969) là lãnh tụ vĩ đại của dân tộc Việt Nam, Chủ tịch nước Việt Nam Dân chủ Cộng hòa. Người là nhà cách mạng, nhà tư tưởng, nhà văn hóa lớn, sáng lập và lãnh đạo Đảng Cộng sản Việt Nam. Hồ Chí Minh dẫn dắt nhân dân Việt Nam đấu tranh giành độc lập, tiêu biểu với Tuyên ngôn Độc lập (1945). Cuộc đời và sự nghiệp của Người gắn liền với sự nghiệp giải phóng dân tộc và xây dựng đất nước.\r\n', 'hochiminh.jpg'),
(4, 'Herbert Schildt', '1951-02-28', 'Nam', 'Chicago, Illinois, Hoa Kỳ', 'Herbert Schildt là nhà văn và lập trình viên người Mỹ, nổi tiếng với các cuốn sách về ngôn ngữ lập trình, đặc biệt là **C, C++, Java và C#**. Ông là tác giả của nhiều sách học lập trình phổ biến dành cho người mới bắt đầu, được xuất bản rộng rãi trên thế giới. Tác phẩm của Herbert Schildt có phong cách trình bày rõ ràng, dễ hiểu, góp phần phổ biến kiến thức lập trình cho nhiều thế hệ người học.\r\n', 'herbert.jpg'),
(5, 'Ernst Gombrich', '1909-03-30', 'Nam', 'Luân Đôn, Vương Quốc Anh', 'Ernst Gombrich (1909–2001) là sử gia và nhà phê bình nghệ thuật người Áo–Anh. Ông nổi tiếng toàn cầu với cuốn Câu chuyện nghệ thuật (The Story of Art), một trong những sách nhập môn về lịch sử nghệ thuật có ảnh hưởng lớn nhất thế kỷ 20. Các công trình của Gombrich đề cao cách tiếp cận nghệ thuật gắn với nhận thức và tâm lý con người.\r\n', 'ernst.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `email`, `role`) VALUES
(1, 'ad1', '12345', '', b'0'),
(2, 'SV01', '09122005', 'a@gmail.com', b'1'),
(5, 'SV012', '789789', 'phuonglinh9005@gmail.com', b'1'),
(6, 'SV015', '123', 'phuonglinh9005@gmail.com', b'1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `yeu_thich`
--

CREATE TABLE `yeu_thich` (
  `id` int(11) NOT NULL,
  `ma_sach` varchar(50) NOT NULL,
  `ma_sv` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `yeu_thich`
--

INSERT INTO `yeu_thich` (`id`, `ma_sach`, `ma_sv`) VALUES
(356, 'S004', 'SV01'),
(358, 'S001', 'SV01'),
(359, 'S002', 'SV01'),
(360, 'S003', 'SV01'),
(361, 'S006', 'SV01'),
(362, 'S001', 'SV015'),
(363, 'S006', 'SV015'),
(364, 'S002', 'SV015'),
(365, 'S004', 'SV015'),
(366, 'S003', 'SV015');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`ma_admin`),
  ADD KEY `id` (`id`);

--
-- Chỉ mục cho bảng `admin_islogin`
--
ALTER TABLE `admin_islogin`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `dang_nhap`
--
ALTER TABLE `dang_nhap`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `hinh_phat`
--
ALTER TABLE `hinh_phat`
  ADD PRIMARY KEY (`ma_hp`),
  ADD KEY `ma_sv` (`ma_sv`),
  ADD KEY `ma_pm` (`ma_pm`);

--
-- Chỉ mục cho bảng `loai_sach`
--
ALTER TABLE `loai_sach`
  ADD PRIMARY KEY (`ma_loai_sach`);

--
-- Chỉ mục cho bảng `phieu_muon`
--
ALTER TABLE `phieu_muon`
  ADD PRIMARY KEY (`ma_pm`),
  ADD KEY `ma_sach` (`ma_sach`),
  ADD KEY `ma_sv` (`ma_sv`);

--
-- Chỉ mục cho bảng `sach`
--
ALTER TABLE `sach`
  ADD PRIMARY KEY (`ma_sach`),
  ADD KEY `ma_loai_sach` (`ma_loai_sach`),
  ADD KEY `sach_ibfk_2` (`ma_tg`);

--
-- Chỉ mục cho bảng `sinh_vien`
--
ALTER TABLE `sinh_vien`
  ADD PRIMARY KEY (`ma_sv`),
  ADD KEY `id` (`id`);

--
-- Chỉ mục cho bảng `tac_gia`
--
ALTER TABLE `tac_gia`
  ADD PRIMARY KEY (`ma_tg`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `yeu_thich`
--
ALTER TABLE `yeu_thich`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ma_sach` (`ma_sach`),
  ADD KEY `ma_sv` (`ma_sv`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `admin_islogin`
--
ALTER TABLE `admin_islogin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT cho bảng `dang_nhap`
--
ALTER TABLE `dang_nhap`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT cho bảng `hinh_phat`
--
ALTER TABLE `hinh_phat`
  MODIFY `ma_hp` int(50) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tac_gia`
--
ALTER TABLE `tac_gia`
  MODIFY `ma_tg` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `yeu_thich`
--
ALTER TABLE `yeu_thich`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=367;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`);

--
-- Các ràng buộc cho bảng `hinh_phat`
--
ALTER TABLE `hinh_phat`
  ADD CONSTRAINT `hinh_phat_ibfk_1` FOREIGN KEY (`ma_sv`) REFERENCES `sinh_vien` (`ma_sv`),
  ADD CONSTRAINT `hinh_phat_ibfk_2` FOREIGN KEY (`ma_pm`) REFERENCES `phieu_muon` (`ma_pm`);

--
-- Các ràng buộc cho bảng `phieu_muon`
--
ALTER TABLE `phieu_muon`
  ADD CONSTRAINT `phieu_muon_ibfk_1` FOREIGN KEY (`ma_sach`) REFERENCES `sach` (`ma_sach`),
  ADD CONSTRAINT `phieu_muon_ibfk_2` FOREIGN KEY (`ma_sv`) REFERENCES `sinh_vien` (`ma_sv`);

--
-- Các ràng buộc cho bảng `sach`
--
ALTER TABLE `sach`
  ADD CONSTRAINT `sach_ibfk_1` FOREIGN KEY (`ma_loai_sach`) REFERENCES `loai_sach` (`ma_loai_sach`),
  ADD CONSTRAINT `sach_ibfk_2` FOREIGN KEY (`ma_tg`) REFERENCES `tac_gia` (`ma_tg`);

--
-- Các ràng buộc cho bảng `sinh_vien`
--
ALTER TABLE `sinh_vien`
  ADD CONSTRAINT `sinh_vien_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`);

--
-- Các ràng buộc cho bảng `yeu_thich`
--
ALTER TABLE `yeu_thich`
  ADD CONSTRAINT `yeu_thich_ibfk_2` FOREIGN KEY (`ma_sach`) REFERENCES `sach` (`ma_sach`),
  ADD CONSTRAINT `yeu_thich_ibfk_3` FOREIGN KEY (`ma_sv`) REFERENCES `sinh_vien` (`ma_sv`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
