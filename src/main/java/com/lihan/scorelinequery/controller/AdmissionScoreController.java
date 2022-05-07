package com.lihan.scorelinequery.controller;

import com.github.pagehelper.PageInfo;
import com.lihan.scorelinequery.Util.AdmissionExcelUtil;
import com.lihan.scorelinequery.entity.AdmissionScore;
import com.lihan.scorelinequery.entity.School;
import com.lihan.scorelinequery.service.AdmissionScoreService;
import com.lihan.scorelinequery.service.ProvinceService;
import com.lihan.scorelinequery.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.Set;

@Slf4j
@Controller
public class AdmissionScoreController {
    @Resource
    AdmissionScoreService admissionScoreService;
    @Resource
    ProvinceService provinceService;
    @Resource
    SchoolService schoolService;

    @RequestMapping(value = "admission_list")
    public String showAdmission(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, String school_name,
                                String province_name, String year, String major, String kind) {
        log.info("school_name:{}", school_name);
        log.info("year:{}", year);

        Integer trueYear = (year == null || year.equals("null") || year == "") ? null : Integer.parseInt(year);

        PageInfo<AdmissionScore> admissionList = admissionScoreService.FindAdmissionScores(pageNum,
                pageSize, school_name, province_name, trueYear, major, kind);
        model.addAttribute("admissionList", admissionList);
        model.addAttribute("school_name", school_name);
        model.addAttribute("province_name", province_name);
        model.addAttribute("year", year);
        model.addAttribute("major", major);
        model.addAttribute("kind", kind);

        return "admission_list";
    }

    @RequestMapping("/admission_delete")
    public String deleteAdmission(Integer admission_id, RedirectAttributes redirectAttributes, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("admission_id:{}", admission_id);
        admissionScoreService.DeleteAdmissionScore(admission_id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:admission_list";
    }

    @RequestMapping("/admission_insert")
    public String insertAdmission(RedirectAttributes redirectAttributes, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, String province_name, String school_name, Integer year,
                                  String major, String kind, Integer least_score, Integer high_score,
                                  Integer avg_score, Integer least_rank, Integer num) {
        String msg= check(province_name,school_name,year, kind, least_score, high_score, avg_score);
        if(msg!=null){
            redirectAttributes.addFlashAttribute("msg", msg);
            return "redirect:admission_list";
        }
        admissionScoreService.InsertAdmissionScore(provinceService.getProvincePid(province_name), schoolService.SelectSchool(school_name).getSchool_id(), school_name, year, major, kind, least_score, high_score, avg_score, least_rank, num);
        redirectAttributes.addFlashAttribute("msg", "添加成功");
        return "redirect:admission_list";
    }

    @RequestMapping("/admission_update")
    public String UpdateAdmission(Integer admission_id, RedirectAttributes redirectAttributes, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, String province_name, String school_name, Integer year,
                                  String major, String kind, Integer least_score, Integer high_score,
                                  Integer avg_score, Integer least_rank, Integer num) {

        String msg=check(province_name,school_name,year, kind, least_score, high_score, avg_score);
        if(msg!=null){
            redirectAttributes.addFlashAttribute("msg", msg);
            return "redirect:admission_list";
        }
        Integer pid = provinceService.getProvincePid(province_name);
        Integer school_id = schoolService.SelectSchool(school_name).getSchool_id();
        admissionScoreService.UpdateAdmissionScore(admission_id, pid, school_id, school_name, year, major, kind, least_score, high_score, avg_score, least_rank, num);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:admission_list";
    }


    @RequestMapping("excel_admission_insert")
    public String SchoolExcelInsert() {
        return "excel_admission_insert";
    }

    @RequestMapping("upload_admission")
    public String ExcelParseAdmission(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        String name = file.getOriginalFilename();
        System.out.println(name);
        if (!name.substring(name.length() - 4).equals(".xls")) {
            redirectAttributes.addFlashAttribute("msg", "文件解析错误");
            return "redirect:excel_admission_insert";
        }
        Set<AdmissionScore> admissionScoreSet = AdmissionExcelUtil.excelToGetAdmissionScore(file.getInputStream());
        if (admissionScoreSet == null) {
            redirectAttributes.addFlashAttribute("msg", "文件解析错误");
            return "redirect:excel_admission_insert";
        }
        for (AdmissionScore admissionScore : admissionScoreSet) {
            String msg=check(admissionScore.getProvince_name(),admissionScore.getSchool_name(),admissionScore.getYear(),
                    admissionScore.getKind(),admissionScore.getLeast_score(),admissionScore.getHigh_score(),admissionScore.getAvg_score());
            if(msg!=null){
                redirectAttributes.addFlashAttribute("msg", "文件解析错误");
                return "redirect:excel_admission_insert";
            }
        }
        for (AdmissionScore admissionScore : admissionScoreSet) {
            Integer pid = provinceService.getProvincePid(admissionScore.getProvince_name());
            if (admissionScoreService.SelectAdmission(pid, admissionScore.getSchool_name(), admissionScore.getYear(), admissionScore.getMajor(), admissionScore.getKind()) != null) {
                continue;
            }
            Integer school_id = schoolService.SelectSchool(admissionScore.getSchool_name()).getSchool_id();

            admissionScoreService.InsertAdmissionScore(pid, school_id, admissionScore.getSchool_name(), admissionScore.getYear(),
                    admissionScore.getMajor(), admissionScore.getKind(), admissionScore.getLeast_score(), admissionScore.getHigh_score(),
                    admissionScore.getAvg_score(), admissionScore.getLeast_rank(), admissionScore.getNum());
        }
        redirectAttributes.addFlashAttribute("msg", "添加成功");
        return "redirect:excel_admission_insert";
    }

    String check(String province_name, String school_name, Integer year,
                  String kind, Integer least_score, Integer high_score,
                  Integer avg_score){

        if(schoolService.SelectSchool(school_name)==null){
            return "学校信息错误";
        }

        if(kind==null||(!kind.equals("文科")&&!kind.equals("理科"))){
            return "文理信息错误";
        }
        if(least_score==null||(least_score>high_score)){
            return "最低分错误";
        }
        if(avg_score!=null&&(avg_score>high_score||avg_score<least_score)){
            return "平均分错误";
        }
        return null;
    }

}
