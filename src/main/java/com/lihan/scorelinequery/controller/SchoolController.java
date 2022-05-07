package com.lihan.scorelinequery.controller;

import com.github.pagehelper.PageInfo;
import com.lihan.scorelinequery.Util.SchoolExcelUtil;
import com.lihan.scorelinequery.entity.School;
import com.lihan.scorelinequery.service.ProvinceService;
import com.lihan.scorelinequery.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.Set;

@Slf4j
@Controller
public class SchoolController {
    @Resource
    SchoolService schoolService;
    @Resource
    ProvinceService provinceService;

    @RequestMapping("/school_list")
    public String showSchool(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             String school_name, String province_name) {
        PageInfo<School> schoolList = schoolService.FindSchool(pageNum, pageSize, school_name, province_name);

        model.addAttribute("schoolList",schoolList);
        model.addAttribute("school_name",school_name);
        model.addAttribute("province_name",province_name);
        return "school_list";
    }

    @RequestMapping("/school_delete")
    public String deleteSchool(Integer school_id, RedirectAttributes redirectAttributes,@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        schoolService.DeleteSchool(school_id);
        redirectAttributes.addFlashAttribute("msg","删除成功");
        return "redirect:school_list";
    }

    @RequestMapping("school_insert")
    public String insertSchool(RedirectAttributes redirectAttributes,@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                               String school_name,String province_name,String site)
    {
        if (school_name =="") {
            redirectAttributes.addFlashAttribute("msg", "添加失败，学校名不能为空");
            return "redirect:school_list";
        }
        Integer pid=provinceService.getProvincePid(province_name);
        if(schoolService.SelectSchool(school_name)!=null){
            redirectAttributes.addFlashAttribute("msg", "添加失败，学校名重复");
            return "redirect:school_list";
        }
        if(province_name!=""&&pid==null){
            redirectAttributes.addFlashAttribute("msg","添加失败，没有这个省份");
            return "redirect:school_list";
        }

        schoolService.InsertSchool(school_name,pid,site);
        redirectAttributes.addFlashAttribute("msg","添加成功");
        return "redirect:school_list";
    }

    @RequestMapping(value = "/school_update", method = RequestMethod.POST)
    public String updateSchool(Integer school_id, String school_name, String province_name,String site, RedirectAttributes redirectAttributes, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        if (school_name =="") {
            redirectAttributes.addFlashAttribute("msg", "修改失败，学校名不能为空");
            return "redirect:school_list";
        }
        Integer pid=provinceService.getProvincePid(province_name);
        if(province_name!=""&&pid==null){
            redirectAttributes.addFlashAttribute("msg","修改失败，没有这个省份");
            return "redirect:school_list";
        }
        schoolService.UpdateSchool(school_id,school_name,pid,site);
        redirectAttributes.addFlashAttribute("msg","修改成功");

        return "redirect:school_list";
    }


    @RequestMapping("excel_school_insert")
    public String SchoolExcelInsert() {
        return "excel_school_insert";
    }

    @RequestMapping("upload_school")
    public String ExcelParseSchool(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        String name = file.getOriginalFilename();
        System.out.println(name);
        if (!name.substring(name.length() - 4).equals(".xls")) {
            redirectAttributes.addFlashAttribute("msg", "文件解析错误");
            return "redirect:excel_school_insert";
        }
        Set<School> schoolSet = SchoolExcelUtil.excelToGetSchool(file.getInputStream());
        for (School school : schoolSet) {
            if(schoolService.SelectSchool(school.getSchool_name())!=null){
                continue;
            }
            Integer pid=provinceService.getProvincePid(school.getProvince_name());
            if(pid==null){
                continue;
            }
            schoolService.InsertSchool(school.getSchool_name(),pid,school.getSite());
        }
        redirectAttributes.addFlashAttribute("msg","添加成功");
        return "redirect:excel_school_insert";
    }
}
