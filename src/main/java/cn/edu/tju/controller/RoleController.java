package cn.edu.tju.controller;


import cn.edu.tju.entity.*;
import cn.edu.tju.service.PermissionService;
import cn.edu.tju.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    //显示所有权限表
    @ResponseBody
    @GetMapping("/list")
    public Object list(
            @RequestParam(defaultValue = "") String name
            , @RequestParam(defaultValue = "") String code
            , @RequestParam(defaultValue = "") String status
            , @RequestParam(defaultValue = "1") Integer page
            , @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) wrapper.like("name", name);
        if (StringUtils.isNotEmpty(code)) wrapper.eq("code", code);
        if (StringUtils.isNotEmpty(status)) wrapper.eq("status", status);
        Page<Role> ipage = new Page<>(page, limit);
        IPage<Role> iPage = roleService.page(ipage, wrapper);
        List<Role> roles = iPage.getRecords();
        getRoleWithPer(roles);
        JsonResult<Role> result = new JsonResult<>(iPage.getTotal(), iPage.getRecords());
        return result;
    }

    private void getRoleWithPer(List<Role> roles) {
        List<RoleAndPer> roleAndPers = roleService.selectWithPers();
        boolean flag = false;
        for (Role r : roles) {
            for (RoleAndPer roleAndPer : roleAndPers) {
                ArrayList<String> pnameList = new ArrayList<>();
                HashMap<Integer, String> pidMap = new HashMap<>();
                if (r.getId() == roleAndPer.getId() || r.getId().equals(roleAndPer.getId())) {
                    List<Permission> rolePers = roleAndPer.getRolePers();
                    for (Permission rolePer : rolePers) {
                        pnameList.add(rolePer.getName());
                        pidMap.put(rolePer.getId(), rolePer.getName());
                    }
                    r.setPers(pnameList);
                    r.set_pids(pidMap);
                    flag = true;
                }
            }
            if (!flag) {
                r.setPers(new ArrayList<>());
                r.set_pids(new HashMap<>());
            }
        }
    }

    //显示当前登录用户的权限表
    @ResponseBody
    @GetMapping("/listself")
    public Object listself() {
        return null;
    }

    @GetMapping({"/", "/index"})
    public String index() {
        return "role/index";
    }


    //跳转添加页面
    @GetMapping("/opt/toAdd")
    public Object toAdd(Model model) {
        List<Permission> list = permissionService.list();
        model.addAttribute("pers", list);
        return "role/add";
    }

    //跳转编辑页面
    @GetMapping("/opt/toEdit/{id}")
    public Object toEdit(@PathVariable Integer id, Model model) {
        List<Permission> list = permissionService.list();
        model.addAttribute("pers", list);
        Role role = (Role) get(id);
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        getRoleWithPer(roles);
        model.addAttribute("role", role);
        return "role/edit";
    }


    //增加
    @ResponseBody
    @PostMapping("/opt")
    public Object add(Role role, @RequestParam(name = "pids[]",required = false) Integer[] pids) {
        role.setGmtCreate(LocalDateTime.now());
        role.setGmtModified(LocalDateTime.now());
        role.setStatus(1);
        boolean b = roleService.saveRole(role, pids);
        ResponseData data = new ResponseData(b, "添加");
        return data;
    }

    //删除
    @ResponseBody
    @DeleteMapping("/opt")
    public Object delete(@RequestBody List<Integer> ids) {
        boolean b = roleService.removeByIds(ids);
        ResponseData data = new ResponseData(b, "删除");
        return data;
    }

    //修改
    @ResponseBody
    @PutMapping("/opt/{id}")
    public Object modified(Role role, @PathVariable Integer id, @RequestParam(name = "pids[]", required = false) Integer[] pids) {
        boolean b;
        role.setGmtModified(LocalDateTime.now());
        b = roleService.updateRole(role, pids);
        ResponseData data = new ResponseData(b, "修改");
        return data;
    }
    //单属性修改
    @ResponseBody
    @PostMapping("/opt/{id}")
    public Object modifiedOne(Role role, @PathVariable Integer id) {
        role.setGmtModified(LocalDateTime.now());
        roleService.updateById(role);
        ResponseData data = new ResponseData(true, "修改");
        return data;
    }

    //查找
    @ResponseBody
    @GetMapping("/opt/{id}")
    public Object get(@PathVariable Integer id) {
        Role per = roleService.getById(id);
        return per;
    }
}
