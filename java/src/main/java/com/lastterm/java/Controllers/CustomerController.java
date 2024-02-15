package com.lastterm.java.Controllers;

import com.lastterm.java.Models.Customer;
import com.lastterm.java.Repositories.CustomerRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController implements ErrorController {

    @Autowired
    private CustomerRepository repo;

    @RequestMapping("")
    public String index(Model model) {
        List<Customer> list = new ArrayList<>();
        repo.findAll().forEach(list::add);

        model.addAttribute("customers", list);

        return "customer/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        repo.save(customer);
        redirectAttributes.addFlashAttribute("successMessage", "Khách hàng đã được lưu thành công.");
        return "redirect:/customers";
    }

    @GetMapping("/edit/{customerId}")
    public String edit(@PathVariable("customerId") String customerId, Model model) {
        Customer customer = repo.findById(customerId).orElse(null);
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "customer/edit";
        } else {
            return "error/404"; // Trang lỗi không tìm thấy khách hàng
        }
    }

    @PostMapping("/edit/{customerId}")
    public String update(@PathVariable("customerId") String customerId, @ModelAttribute Customer updatedCustomer, RedirectAttributes redirectAttributes) {
        Customer existingCustomer = repo.findById(customerId).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setCustomerName(updatedCustomer.getCustomerName());
            existingCustomer.setCustomerAge(updatedCustomer.getCustomerAge());
            existingCustomer.setCustomerPhone(updatedCustomer.getCustomerPhone());
            existingCustomer.setProduct(updatedCustomer.getProduct());

            repo.save(existingCustomer);
            redirectAttributes.addFlashAttribute("message", "Thông tin khách hàng đã được cập nhật thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy khách hàng hoặc không có quyền cập nhật.");
        }
        return "redirect:/customers";
    }

    @PostMapping("/delete")
    public String delete(Model model, HttpServletRequest request) {
        String idToDelete = request.getParameter("idToDelete");
        repo.deleteById(idToDelete);
        model.addAttribute("successMessage", "Khách hàng đã được xóa thành công.");
        return "redirect:/customers";
    }

    @GetMapping("/error")
    public String errorHandler(HttpServletRequest req, Model model) {
        int code = (int) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String url = (String) req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        model.addAttribute("code", code);
        model.addAttribute("url", url);
        return "error/genericError"; // Trang lỗi chung
    }
}
