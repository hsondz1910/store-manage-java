package com.lastterm.java.Controllers;

import com.lastterm.java.Models.Employee;
import com.lastterm.java.Repositories.EmployeeRepository;
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
@RequestMapping("/employees")
public class EmployeeController implements ErrorController {

    @Autowired
    private EmployeeRepository repo;

    @RequestMapping("")
    public String index(Model model) {
        List<Employee> list = new ArrayList<>();
        repo.findAll().forEach(list::add);

        model.addAttribute("employees", list);

        return "employee/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        repo.save(employee);
        redirectAttributes.addFlashAttribute("successMessage", "Nhân viên đã được lưu thành công.");
        return "redirect:/employees";
    }

    @GetMapping("/edit/{employeeId}")
    public String edit(@PathVariable("employeeId") String employeeId, Model model) {
        Employee employee = repo.findById(employeeId).orElse(null);
        if (employee != null) {
            model.addAttribute("employee", employee);
            return "employee/edit";
        } else {
            return "error/404"; // Trang lỗi không tìm thấy người dùng
        }
    }

    @PostMapping("/edit/{employeeId}")
    public String update(@PathVariable("employeeId") String employeeId, @ModelAttribute Employee updatedEmployee, RedirectAttributes redirectAttributes) {
        Employee existingEmployee = repo.findById(employeeId).orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
            existingEmployee.setEmployeeAge(updatedEmployee.getEmployeeAge());
            existingEmployee.setEmployeePhone(updatedEmployee.getEmployeePhone());
            existingEmployee.setEmployeeAddress(updatedEmployee.getEmployeeAddress());

            repo.save(existingEmployee);
            redirectAttributes.addFlashAttribute("message", "Thông tin nhân viên đã được cập nhật thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy nhân viên hoặc không có quyền cập nhật.");
        }
        return "redirect:/employees";
    }

//    @PostMapping("/delete/{employeeId}")
//    public String delete(@PathVariable("employeeId") String employeeId, RedirectAttributes redirectAttributes) {
//        Employee employee = repo.findById(employeeId).orElse(null);
//        if (employee != null) {
//            repo.deleteById(employeeId);
//            redirectAttributes.addFlashAttribute("message", "Nhân viên đã được xóa thành công!");
//        } else {
//            redirectAttributes.addFlashAttribute("message", "Không tìm thấy nhân viên hoặc không có quyền xóa.");
//        }
//        return "redirect:/employees";
//    }

    @PostMapping("/delete")
    public String delete(Model model, HttpServletRequest request) {
        String idToDelete = request.getParameter("idToDelete");
        repo.deleteById(idToDelete);
        model.addAttribute("successMessage", "Nhân viên đã được xóa thành công.");
        return "redirect:/employees";
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
