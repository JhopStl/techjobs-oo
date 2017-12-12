package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
            public String index(Model model, @RequestParam("id") int id) {
        //push the data to view by pulling the data from jobData by ID
       model.addAttribute("job", jobData.findById(id));
        return "job-detail";
    }

    //displayAdd
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }
    //processAdd
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {
        if (errors.hasErrors()) {
            return "new-job";
        } else {

            //pull job data from form and create job field classes from data
            String aName = jobForm.getName();
           Employer anEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
           Location aLocation = jobData.getLocations().findById(jobForm.getLocationId());
           PositionType aPosition = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
           CoreCompetency aSkill = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());

           //construct new job with job fields and data
            Job test = new Job(aName, anEmployer,aLocation,aPosition,aSkill);
            //add new job to Job Data
            jobData.add(test);
            return "redirect:" + "?id=" + test.getId();
        }
        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.


    }
}
