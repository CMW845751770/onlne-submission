package cn.edu.tju.controller;


import cn.edu.tju.entity.*;
import cn.edu.tju.service.RoleService;
import cn.edu.tju.service.UserService;
import cn.edu.tju.service.UserdataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@Controller
@RequestMapping("/admin/user")
@Slf4j
public class UserController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserdataService userdataService ;

    @RequestMapping(value = {"/password"})
    public String password(HttpServletRequest request) {
        return "/user/password";
    }


    //修改密码
    @ResponseBody
    @PutMapping("/opt/password")
    public Object modifiedPassword(String passwordOld,String passwordNew) {
        if(StringUtils.isEmpty(passwordOld) || StringUtils.isEmpty(passwordNew)){
            return new ResponseData(false,"修改") ;
        }
        UserDetails userDetails = userService.getCurrentUser() ;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userDetails.getUsername());
        User user = userService.getOne(wrapper);
        if(!passwordEncoder.matches(passwordOld,user.getPassword())){
            return new ResponseData(false,"修改") ;
        }
        boolean b;
        user.setGmtModified(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(passwordNew));
        b = userService.updateById(user) ;
        return new ResponseData(b, "修改");
    }

    //显示所有权限表
    @ResponseBody
    @GetMapping("/list")
    public Object list(
            @RequestParam(defaultValue = "") String username
            , @RequestParam(defaultValue = "") String realname
            , @RequestParam(defaultValue = "") String email
            , @RequestParam(defaultValue = "") String status
            , @RequestParam(defaultValue = "") String type
            , @RequestParam(defaultValue = "1") Integer page
            , @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(username)) wrapper.like("username", username);
        if (StringUtils.isNotEmpty(realname)) wrapper.like("realname", realname);
        if (StringUtils.isNotEmpty(email)) wrapper.eq("email", email);
        if (StringUtils.isNotEmpty(status)) wrapper.eq("status", status);
        if (StringUtils.isNotEmpty(type)) wrapper.eq("type", type);
        Page<User> ipage = new Page<>(page, limit);
        IPage<User> iPage = userService.page(ipage, wrapper);
        getUserWithRole(iPage.getRecords());
        JsonResult<User> result = new JsonResult<>(iPage.getTotal(), iPage.getRecords());
        return result;
    }

    @GetMapping({"/", "/index"})
    public String index() {
        return "user/index";
    }


    //跳转添加页面
    @GetMapping("/opt/toAdd")
    public Object toAdd(Model model) {
        List<Role> list = roleService.list();
        model.addAttribute("roles", list);
        return "user/add";
    }

    //跳转编辑页面
    @GetMapping("/opt/toEdit/{id}")
    public Object toEdit(@PathVariable Integer id, Model model) {
        List<Role> list = roleService.list();
        model.addAttribute("roles", list);
        User user = (User) get(id);
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        getUserWithRole(users);
        model.addAttribute("user", user);
        return "user/edit";
    }

    private void getUserWithRole(List<User> users) {
        List<UserAndRole> userAndRoles = userService.selectWithRoles();
        boolean flag = false;
        for (User u : users) {
            for (UserAndRole userAndRole : userAndRoles) {
                ArrayList<String> rnameList = new ArrayList<>();
                Map<Integer, String> ridMap = new HashMap<>();
                if (u.getId() == userAndRole.getId() || u.getId().equals(userAndRole.getId())) {
                    List<Role> rolePers = userAndRole.getUserRoles();
                    for (Role rolePer : rolePers) {
                        rnameList.add(rolePer.getName());
                        ridMap.put(rolePer.getId(), rolePer.getName());
                    }
                    u.setRoles(rnameList);
                    u.set_rids(ridMap);
                    flag = true;
                }
            }
            if (!flag) {
                u.setRoles(new ArrayList<>());
                u.set_rids(new HashMap<>());
            }
        }
    }

    //增加
    @ResponseBody
    @PostMapping("/opt")
    public Object add(User user, @RequestParam(name = "rids[]", required = false) Integer[] rids) {
        user.setGmtCreate(LocalDateTime.now());
        user.setGmtModified(LocalDateTime.now());
        user.setStatus(1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean b = userService.saveUser(user, rids);
        if (b) {
            //插入userdata数据
            Userdata userdata = new Userdata();
            BeanUtils.copyProperties(user, userdata);
            userdata.setGmtModified(LocalDateTime.now())
                    .setGmtCreate(LocalDateTime.now())  ;
            userdataService.saveOrUpdate(userdata) ;

        }
        ResponseData data = new ResponseData(b, "添加");
        return data;
    }

    //删除
    @ResponseBody
    @DeleteMapping("/opt")
    public Object delete(@RequestBody List<Integer> ids) {
        boolean b = userService.removeByIds(ids);
        ResponseData data = new ResponseData(b, "删除");
        return data;
    }

    //修改
    @ResponseBody
    @PutMapping("/opt/{id}")
    public Object modified(User user, @PathVariable Integer id, @RequestParam(name = "rids[]", required = false) Integer[] rids) {
        boolean b;
        user.setGmtModified(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        b = userService.updateUser(user, rids);
        ResponseData data = new ResponseData(b, "修改");
        return data;
    }

    //单属性修改
    @ResponseBody
    @PostMapping("/opt/{id}")
    public Object modifiedOne(User user, @PathVariable Integer id) {
        user.setGmtModified(LocalDateTime.now());
        userService.updateById(user);
        ResponseData data = new ResponseData(true, "修改");
        return data;
    }

    //查找
    @ResponseBody
    @GetMapping("/opt/{id}")
    public Object get(@PathVariable Integer id) {
        User user = userService.getById(id);
        return user;
    }

    //根据username查找
    @ResponseBody
    @GetMapping("/opt")
    public Object get(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userService.getOne(wrapper);
        ResponseData<User> data;
        if (user == null) {
            data = new ResponseData<User>(0, "可以使用该用户名");
        } else {
            data = new ResponseData<User>(1, "此用户名已被占用！");
        }
        return data;
    }
}
