package cn.edu.tju.controller;


import cn.edu.tju.entity.RoleAndPer;
import cn.edu.tju.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/admin/role-permission")
public class RolePermissionController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public Object list() {
        List<RoleAndPer> list = roleService.selectWithPers();
        return list;
    }
}
