package cn.edu.tju.controller;


import cn.edu.tju.entity.JsonResult;
import cn.edu.tju.entity.Permission;
import cn.edu.tju.entity.ResponseData;
import cn.edu.tju.service.PermissionService;
import cn.edu.tju.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/admin/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;

    //显示所有权限表
    @ResponseBody
    @GetMapping("/list")
    public Object list(
            @RequestParam(defaultValue = "") String name
            , @RequestParam(defaultValue = "") String url
            , @RequestParam(defaultValue = "") String type
            , @RequestParam(defaultValue = "") String gmtCreate
            , @RequestParam(defaultValue = "") String gmtModified
            , @RequestParam(defaultValue = "1") Integer page
            , @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) wrapper.like("name", name);
        if (StringUtils.isNotEmpty(url)) wrapper.eq("url", url);
        if (StringUtils.isNotEmpty(type)) wrapper.eq("type", type);
        if (StringUtils.isNotEmpty(gmtCreate)) {
            String[] create = gmtCreate.split(" - ");
            wrapper.between("gmt_create", create[0], create[1]);
        }
        if (StringUtils.isNotEmpty(gmtModified)) {
            String[] modified = gmtModified.split(" - ");
            wrapper.between("gmt_modified", modified[0], modified[1]);
        }
        IPage<Permission> iPage = permissionService.page(new Page<Permission>(page, limit), wrapper);
        JsonResult<Permission> result = new JsonResult<>(iPage.getTotal(), iPage.getRecords());
        return result;
    }

    //显示当前登录用户的权限表
    @ResponseBody
    @GetMapping("/listself")
    public Object listself() {
        Collection<? extends GrantedAuthority> authorities =
                userService.getCurrentUser().getAuthorities();
        List<Permission> list = permissionService.findByAuths(authorities);
        System.out.println(authorities);
        return list;
    }

    @GetMapping({"/", "/index"})
    public Object index() {
        return "permission/index";
    }


    //跳转添加页面
    @GetMapping("/opt/toAdd")
    public Object toAdd(Model model) {
        List<Permission> list = permissionService.list();
        model.addAttribute("pers", list);
        return "permission/add";
    }

    //跳转编辑页面
    @GetMapping("/opt/toEdit/{id}")
    public Object toEdit(@PathVariable Integer id, Model model) {
        Permission per = (Permission) get(id);
        model.addAttribute("per", per);
        List<Permission> list = permissionService.list();
        model.addAttribute("pers", list);
        return "permission/edit";
    }


    //增加
    @ResponseBody
    @PostMapping("/opt")
    public Object add(Permission per) {
        per.setGmtCreate(LocalDateTime.now());
        per.setGmtModified(LocalDateTime.now());
        boolean b = permissionService.save(per);
        ResponseData data = new ResponseData(b, "添加");
        return data;
    }

    //删除
    @ResponseBody
    @DeleteMapping("/opt")
    public Object delete(@RequestBody List<Integer> ids) {
        boolean b = permissionService.removeByIds(ids);
        ResponseData data = new ResponseData(b, "删除");
        return data;
    }

    //修改
    @ResponseBody
    @PutMapping("/opt/{id}")
    public Object modified(Permission per, @PathVariable Integer id) {
        per.setGmtModified(LocalDateTime.now());
        boolean b = permissionService.updateById(per);
        ResponseData data = new ResponseData(b, "修改");
        return data;
    }

    //查找
    @ResponseBody
    @GetMapping("/opt/{id}")
    public Object get(@PathVariable Integer id) {
        Permission per = permissionService.getById(id);
        return per;
    }
}
