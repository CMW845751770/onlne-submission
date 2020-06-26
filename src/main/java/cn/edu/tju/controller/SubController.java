package cn.edu.tju.controller;


import cn.edu.tju.entity.*;
import cn.edu.tju.service.MsgService;
import cn.edu.tju.service.SubFileService;
import cn.edu.tju.service.SubService;
import cn.edu.tju.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@Controller
@RequestMapping("/admin/sub")
public class SubController {
    @Autowired
    private SubService subService;
    @Autowired
    private SubFileService subFileService;
    @Autowired
    private MsgService msgService;
    @Autowired
    private UserService userService;

    //跳转投稿页面
    @RequestMapping("/put")
    public String put() {
        return "sub/put";
    }

    //添加投稿
    @ResponseBody
    @PostMapping("/put")
    public Object addSub(Sub sub, @RequestParam(name = "fids[]", required = false) String[] fids) {
        sub.setUsername(userService.getCurrentUser().getUsername());
        boolean b = subService.saveSub(sub, fids);
        ResponseData data = new ResponseData(b, "添加");
        return data;
    }

    //显示所有稿件表
    @ResponseBody
    @GetMapping("/list")
    public Object list(
            @RequestParam(defaultValue = "") String author
            , @RequestParam(defaultValue = "") String username
            , @RequestParam(defaultValue = "") String title
            , @RequestParam(defaultValue = "") String summary
            , @RequestParam(defaultValue = "") String keyword
            , @RequestParam(defaultValue = "") String subject
            , @RequestParam(defaultValue = "") String status
            , @RequestParam(defaultValue = "") String gmtCreate
            , @RequestParam(defaultValue = "") String gmtModified
            , @RequestParam(defaultValue = "1") Integer page
            , @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Sub> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(author)) wrapper.like("author", author);
        if (StringUtils.isNotEmpty(username)) wrapper.eq("username", username);
        if (StringUtils.isNotEmpty(title)) wrapper.like("title", title);
        if (StringUtils.isNotEmpty(summary)) {
            wrapper.like("summary", summary).or().like("summary_en", summary);
        }
        if (StringUtils.isNotEmpty(keyword)) {
            wrapper.like("keyword", keyword).or().like("keyword_en", keyword);
        }
        if (StringUtils.isNotEmpty(subject)) {
            wrapper.like("subject", subject).or().like("subject_en", subject);
        }
        if (StringUtils.isNotEmpty(status)) wrapper.eq("status", status);

        if (StringUtils.isNotEmpty(gmtCreate)) {
            String[] create = gmtCreate.split(" - ");
            wrapper.between("gmt_create", create[0], create[1]);
        }
        if (StringUtils.isNotEmpty(gmtModified)) {
            String[] modified = gmtModified.split(" - ");
            wrapper.between("gmt_modified", modified[0], modified[1]);
        }
        IPage<Sub> iPage = subService.page(new Page<Sub>(page, limit), wrapper);
        JsonResult<Sub> result = new JsonResult<>(iPage.getTotal(), iPage.getRecords());
        return result;
    }

    @GetMapping({"/", "/index"})
    public Object index() {
        return "sub/index";
    }

    @GetMapping("/self")
    public Object self() {
        return "sub/self";
    }

    //显示所有稿件表
    @ResponseBody
    @GetMapping("/listself")
    public Object listself(
            @RequestParam(defaultValue = "") String author
            , @RequestParam(defaultValue = "") String title
            , @RequestParam(defaultValue = "") String summary
            , @RequestParam(defaultValue = "") String keyword
            , @RequestParam(defaultValue = "") String subject
            , @RequestParam(defaultValue = "") String status
            , @RequestParam(defaultValue = "") String gmtCreate
            , @RequestParam(defaultValue = "") String gmtModified
            , @RequestParam(defaultValue = "1") Integer page
            , @RequestParam(defaultValue = "10") Integer limit) {
        UserDetails currentUser = userService.getCurrentUser();
        return list(author, currentUser.getUsername(), title, summary, keyword, subject,
                status, gmtCreate, gmtModified, page, limit);
    }

    //跳转添加页面
    @GetMapping("/opt/toAdd")
    public Object toAdd(Model model) {
        return "sub/add";
    }

    //跳转编辑页面
    @GetMapping("/opt/toEdit/{id}")
    public Object toEdit(@PathVariable Integer id, Model model) {
        Sub sub = (Sub) get(id);
        List<File> files = subFileService.selectBySid(id);
        model.addAttribute("sub", sub);
        model.addAttribute("files", files);
        return "sub/edit";
    }

    //跳转编辑页面
    @GetMapping("/self/opt/toEdit/{id}")
    public Object toEdit_self(@PathVariable Integer id, Model model) {
        if (isSelf(id)) {
            Sub sub = (Sub) get(id);
            List<File> files = subFileService.selectBySid(id);
            model.addAttribute("sub", sub);
            model.addAttribute("files", files);
            return "sub/sedit";
        } else {
            return "";
        }
    }

    //增加
    @ResponseBody
    @PostMapping("/opt")
    public Object add(Sub sub) {
        sub.setGmtCreate(LocalDateTime.now());
        sub.setGmtModified(LocalDateTime.now());
        boolean b = subService.save(sub);
        ResponseData data = new ResponseData(b, "添加");
        return data;
    }

    //删除
    @ResponseBody
    @DeleteMapping("/opt")
    public Object delete(@RequestBody List<Integer> ids) {
        boolean b = subService.removeByIds(ids);
        ResponseData data = new ResponseData(b, "删除");
        return data;
    }

    //修改
    @ResponseBody
    @PutMapping("/opt/{id}")
    public Object modified(Sub sub
            , @RequestParam(name = "fids[]", required = false) String[] fids
            , @PathVariable Integer id) {
        sub.setGmtModified(LocalDateTime.now());
        boolean b = subService.updateSub(sub, fids);
        ResponseData data = new ResponseData(b, "修改");
        return data;
    }

    //修改
    @ResponseBody
    @PutMapping("/self/opt/{id}")
    public Object modified_self(Sub sub
            , @RequestParam(name = "fids[]", required = false) String[] fids
            , @PathVariable Integer id) {
        ResponseData data;
        if (isSelf(sub.getId())) {
            sub.setGmtModified(LocalDateTime.now());
            boolean b = subService.updateSub(sub, fids);
            data = new ResponseData(b, "修改");
        } else {
            data = new ResponseData(false, "修改");
        }
        return data;
    }
    //修改
    @ResponseBody
    @PostMapping("/self/opt/{id}")
    public Object modified_self_one(Sub sub
            , @PathVariable Integer id) {
        ResponseData data;
        if (isSelf(sub.getId())) {
            sub.setGmtModified(LocalDateTime.now());
            boolean b = subService.updateById(sub);
            data = new ResponseData(b, "修改");
        } else {
            data = new ResponseData(false, "修改");
        }
        return data;
    }
    //单属性修改
    @ResponseBody
    @PostMapping("/opt/{id}")
    public Object modifiedOne(Sub sub, @PathVariable Integer id
            ,@RequestParam(name = "msg",defaultValue = "") String msg) {
        boolean b = subService.updateById(sub,msg);
        ResponseData data = new ResponseData(b, "修改");
        return data;
    }

    //查看详情
    @GetMapping("/detail/{id}")
    public Object detail(@PathVariable Integer id, Model model) {
        Sub sub = (Sub) get(id);
        List<File> files = subFileService.selectBySid(id);
        QueryWrapper<Msg> wrapper = new QueryWrapper<>();
        wrapper.eq("sid", id);
        wrapper.orderByDesc("gmt_create");
        List<Msg> msgs = msgService.list(wrapper);
        model.addAttribute("sub", sub);
        model.addAttribute("files", files);
        model.addAttribute("msgs", msgs);

        return "sub/detail";
    }

    @GetMapping("/detail2/{id}")
    public Object detail2(@PathVariable Integer id, Model model) {
        Sub sub = (Sub) get(id);
        List<File> files = subFileService.selectBySid(id);
        QueryWrapper<Msg> wrapper = new QueryWrapper<>();
        wrapper.eq("sid", id);
        wrapper.orderByDesc("gmt_create");
        List<Msg> msgs = msgService.list(wrapper);
        model.addAttribute("sub", sub);
        model.addAttribute("files", files);
        model.addAttribute("msgs", msgs);

        return "sub/detail2";
    }

    //查看详情
    @GetMapping("/self/detail/{id}")
    public Object detail_self(@PathVariable Integer id, Model model) {
        boolean b = isSelf(id);
        if (b) {
            return detail(id,model);
        } else {
            return "";
        }
    }

    //根据id判断是否是自己的稿件
    private boolean isSelf(Integer id) {
        UserDetails currentUser = userService.getCurrentUser();
        QueryWrapper<Sub> wrapper = new QueryWrapper<>();
        wrapper.eq("username", currentUser.getUsername());
        List<Sub> list = subService.list(wrapper);
        for (Sub sub : list) {
            if (id == sub.getId()) return true;
        }
        return false;
    }


    //查找
    @ResponseBody
    @GetMapping("/opt/{id}")
    public Object get(@PathVariable Integer id) {
        Sub sub = subService.getById(id);
        return sub;
    }
}
