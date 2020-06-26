package cn.edu.tju.controller;

import cn.edu.tju.entity.Permission;
import cn.edu.tju.service.PermissionService;
import cn.edu.tju.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @RequestMapping(value = {"/admin/index", "/admin"})
    public String index(Model model) {
        UserDetails currentUser = userService.getCurrentUser();
        List<Permission> permissions = permissionService.findByUsername(currentUser.getUsername());
        model.addAttribute("permissions",permissions);
        return "admin/index";
    }

    @RequestMapping("/admin/welcome")
    public String welcome() {
        return "admin/welcome";
    }

    @RequestMapping(value = {"/index", "/"})
    public String index() {
        return "index";
    }

    @RequestMapping(value = {"/notice"})
    public String notice() {
        return "/notice";
    }
}
