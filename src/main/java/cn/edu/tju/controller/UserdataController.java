package cn.edu.tju.controller;


import cn.edu.tju.entity.ResponseData;
import cn.edu.tju.entity.Userdata;
import cn.edu.tju.service.UserService;
import cn.edu.tju.service.UserdataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@Controller
@RequestMapping("/admin/userdata")
public class UserdataController {

    @Autowired
    private UserService userService ;

    @Autowired
    private UserdataService userdataService ;

    @GetMapping("/self")
    public Object listself(HttpServletRequest request) {
        UserDetails user = userService.getCurrentUser();
        QueryWrapper<Userdata> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        Userdata userdata = userdataService.getOne(wrapper);
        request.setAttribute("user",userdata);
        return "/userdata/self";
    }

    @ResponseBody
    @PutMapping("/opt/{id}")
    public Object modified(Userdata user, @PathVariable Integer id) {
        user.setGmtModified(LocalDateTime.now());
        userdataService.updateById(user) ;
        ResponseData data = new ResponseData(true, "修改");
        return data ;
    }
}
