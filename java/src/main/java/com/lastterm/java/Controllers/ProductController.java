package com.lastterm.java.Controllers;

import com.lastterm.java.Models.Product;
import com.lastterm.java.Repositories.ProductRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController implements ErrorController {

    @Autowired
    private ProductRepository db;

    @RequestMapping("")
    public String index(Model model) {
        List<Product> list = new ArrayList<>();
        db.findAll().forEach(list::add);

        model.addAttribute("products", list);

        return "product/index";
    }

    // Hiển thị form để thêm sản phẩm mới
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", new Product());
        return "product/add";
    }

    // Xử lý thêm sản phẩm mới
    @PostMapping("/add")
    public String add(Product product, Model model) {
        db.save(product);
        model.addAttribute("successMessage", "Sản phẩm đã được lưu thành công.");
        return "redirect:/products";
    }

    // Hiển thị form chỉnh sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        Product product = db.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "product/edit";
    }

    // Cập nhật thông tin sản phẩm
    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") String id, Product updatedProduct, Model model) {
//        db.save(product);
//        model.addAttribute("successMessage", "Sản phẩm đã được cập nhật thành công.");
//        return "redirect:/products";
        // Kiểm tra sự tồn tại của sản phẩm trong database
        Product existingProduct = db.findById(id).orElse(null);

        if (existingProduct != null) {
            // Copy thông tin từ updatedProduct vào existingProduct
            existingProduct.setBarCode(updatedProduct.getBarCode());
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setImportPrice(updatedProduct.getImportPrice());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setDateCreated(updatedProduct.getDateCreated());

            // Lưu lại vào database
            db.save(existingProduct);

            model.addAttribute("successMessage", "Sản phẩm đã được cập nhật thành công.");
            return "redirect:/products";
        } else {
            model.addAttribute("errorMessage", "Không tìm thấy sản phẩm để cập nhật.");
            return "redirect:/products";
        }
    }

    // Xóa sản phẩm
//    @PostMapping("/delete/{id}")
//    public String delete(@PathVariable("id") String id, Model model) {
//        db.deleteById(id);
//        model.addAttribute("successMessage", "Sản phẩm đã được xóa thành công.");
//        return "redirect:/products";
//    }

    // Xóa sản phẩm
//    @PostMapping("/delete")
//    public String delete(Model model, HttpServletRequest request) {
//        String idToDelete = request.getParameter("idToDelete");
//        db.deleteById(idToDelete);
//        model.addAttribute("successMessage", "Sản phẩm đã được xóa thành công.");
//        return "redirect:/products";
//    }

    @PostMapping("/delete")
    public String delete(Model model, HttpServletRequest request) {
        String idToDelete = request.getParameter("idToDelete");
        db.deleteById(idToDelete);
        model.addAttribute("successMessage", "Sản phẩm đã được xóa thành công.");
        return "redirect:/products";
    }


    @GetMapping("/error")
//    @ResponseBody
    public String errorHandler(HttpServletRequest req, Model model) {
        int code = (int) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String url = (String) req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        model.addAttribute("code", code);
        model.addAttribute("url", url);

//        return "An error occured: " + url + ", " + code;
        return "user/error";
    }
}
