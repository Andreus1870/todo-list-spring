package org.example.controller;

import org.example.entity.Record;
import org.example.entity.RecordStatus;
import org.example.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommonController {
    private final RecordService recordService;

    @Autowired
    public CommonController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping("/")
    public String redirectToMainPage() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String getMainPage(Model model, HttpSession session) {
        if (session.isNew()) {
            session.setAttribute("selectedFilter", "all");
        }
        List<Record> records = recordService.findAllRecords();
        if ("active".equals(session.getAttribute("selectedFilter"))) {
            records = recordService.findByStatus(RecordStatus.ACTIVE);
        } else if ("done".equals(session.getAttribute("selectedFilter"))) {
            records = recordService.findByStatus(RecordStatus.DONE);
        }
        int numberOfDoneRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.DONE).count();
        int numberOfActiveRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.ACTIVE).count();
        model.addAttribute("numberOfDoneRecords", numberOfDoneRecords);
        model.addAttribute("records", records);
        model.addAttribute("numberOfActiveRecords", numberOfActiveRecords);
        return "main-page";
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public String addRecord(@RequestParam String title) {
        recordService.saveRecords(title);
        return "redirect:/home";
    }

    @RequestMapping(value = "/make-record-done", method = RequestMethod.POST)
    public String makeRecordDone(@RequestParam int id) {
        recordService.updateRecordStatus(id, RecordStatus.DONE);
        return "redirect:/home";
    }

    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public String deleteRecord(@RequestParam int id) {
        recordService.deleteRecord(id);
        return "redirect:/home";
    }

    @RequestMapping(value = "/change-filter", method = RequestMethod.GET)
    public String changeFilter(@RequestParam(required = false) String filter, HttpSession session) {
        if (filter != null){
            session.setAttribute("selectedFilter", filter);
        }
        return "redirect:/home";
    }
}
