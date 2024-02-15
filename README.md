# Hướng dẫn cài đặt và sử dụng

## Yêu cầu

- Đảm bảo rằng bạn đã cài đặt MongoDB Compass và đã mở server MongoDB.
- Phiên bản Java của bạn phải từ 17 trở lên.

## Chạy ứng dụng và thêm dữ liệu mẫu

1. Mở file `JavaApplication` và dán đoạn mã sau vào để thêm một người dùng mẫu vào cơ sở dữ liệu:
   
```java
@Bean
CommandLineRunner runner(UserRepository repository) {
    return args -> {
        User user  = new User("admin@gmail.com", "admin", "123456", 20, "0123456789", "admin");
        repository.insert(user);
    };
}

2. Thêm các thư viện hỗ trợ vào file JavaApplication sau khi đã thêm đoạn mã trên để tránh lỗi:

    import org.springframework.boot.CommandLineRunner;
    import org.springframework.context.annotation.Bean;
    import com.lastterm.java.Repository.UserRepository;
    import com.lastterm.java.Models.User;

3. Dán vào ngay dưới hàng sau để chạy ứng dụng:
    public static void main(String[] args) {
        SpringApplication.run(JavaApplication.class, args);
    }

Chạy ứng dụng một lần và sau đó xóa đoạn mã thêm người dùng mẫu để có thể đăng nhập một cách suôn sẻ.

4. Các liên kết quan trọng
    Để vào trang đăng nhập: http://localhost:8080/accounts/login
    Để vào trang Quản lý Sản phẩm: http://localhost:8080/products
    Để vào trang Quản lý Nhân viên: http://localhost:8080/employees
    Để vào trang Quản lý Khách hàng: http://localhost:8080/customers