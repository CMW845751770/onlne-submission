package cn.edu.tju.controller;


import cn.edu.tju.entity.JsonResult;
import cn.edu.tju.entity.Msg;
import cn.edu.tju.entity.ResponseData;
import cn.edu.tju.service.MsgService;
import cn.edu.tju.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@Controller
@RequestMapping("/admin/msg")
public class MsgController {
    @Autowired
    private MsgService msgService;
    @Autowired
    private UserService userService;

    //显示所有回复
    @ResponseBody
    @GetMapping("/list")
    public Object list(
            @RequestParam(defaultValue = "") String msg
            , @RequestParam(defaultValue = "") String type
            , @RequestParam(defaultValue = "") String userSender
            , @RequestParam(defaultValue = "") String userReceer
            , @RequestParam(defaultValue = "") String gmtCreate
            , @RequestParam(defaultValue = "1") Integer page
            , @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Msg> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(msg)) wrapper.like("name", msg);
        if (StringUtils.isNotEmpty(type)) wrapper.eq("type", type);
        if (StringUtils.isNotEmpty(userSender)) wrapper.eq("userSender", userSender);
        if (StringUtils.isNotEmpty(userReceer)) wrapper.eq("userReceer", userReceer);
        if (StringUtils.isNotEmpty(gmtCreate)) {
            String[] create = gmtCreate.split(" - ");
            wrapper.between("gmt_create", create[0], create[1]);
        }

        IPage<Msg> iPage = msgService.page(new Page<>(page, limit), wrapper);
        JsonResult<Msg> result = new JsonResult<>(iPage.getTotal(), iPage.getRecords());
        return result;
    }

    //显示当前登录用户的回复
    @ResponseBody
    @GetMapping("/listself")
    public Object listself() {
        Collection<? extends GrantedAuthority> authorities =
                userService.getCurrentUser().getAuthorities();
        //List<Permission> list = msgService.findByAuths(authorities);
        //System.out.println(authorities);
        return null;
    }

    @GetMapping({"/", "/index"})
    public Object index() {
        return "msg/index";
    }

    //增加
    @ResponseBody
    @PostMapping("/opt")
    public Object add(Msg msg) {
        UserDetails currentUser = userService.getCurrentUser();
        msg.setUserSender(currentUser.getUsername());
        msg.setGmtCreate(LocalDateTime.now());
        boolean b = msgService.save(msg);
        ResponseData data = new ResponseData(b, "添加");
        return data;
    }

    //删除
    @ResponseBody
    @DeleteMapping("/opt")
    public Object delete(@RequestBody List<Integer> ids) {
        boolean b = msgService.removeByIds(ids);
        ResponseData data = new ResponseData(b, "删除");
        return data;
    }

    //修改
    @ResponseBody
    @PutMapping("/opt/{id}")
    public Object modified(Msg msg
            , @PathVariable Integer id) {
        boolean b = msgService.updateById(msg);
        ResponseData data = new ResponseData(b, "修改");
        return data;
    }

    //查找
    @ResponseBody
    @GetMapping("/opt/{id}")
    public Object get(@PathVariable Integer id) {
        Msg msg = msgService.getById(id);
        return msg;
    }
}
