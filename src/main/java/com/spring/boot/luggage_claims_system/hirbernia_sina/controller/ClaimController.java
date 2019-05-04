package com.spring.boot.luggage_claims_system.hirbernia_sina.controller;

import com.spring.boot.luggage_claims_system.hirbernia_sina.domain.*;
import com.spring.boot.luggage_claims_system.hirbernia_sina.service.SecurityDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Liu Dairui
 * @date 2019-03-27 21:59
 */
@Controller
@RequestMapping("/claim")
public class ClaimController {

    @Autowired
    private SecurityDataService securityDataService;

    @Autowired
    private JavaMailSender mailSender;

    public static boolean isValidFileName(String fileName) {
        if (fileName == null || fileName.length() > 255) return false;
        else
            return fileName.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
    }

    @GetMapping
    public String claim() {
        return "redirect:/claim/write";
    }

    /**
     * @api {GET} /claim/policy get all policies
     * @apiVersion 1.0.0
     * @apiName get policies
     * @apiGroup claim
     * @apiSuccess success true
     */
    @GetMapping("/policy")
    public String policy(Authentication authentication, Model model) {
        UserInfo customer = securityDataService.getUserByEmailAddress(authentication.getName());
        List<Policy> policies = securityDataService.getAllPoliciesByCustomerId(customer.getId());
        model.addAttribute("policies", policies);
        return "claim/policy";
    }

    /**
     * @param
     * @return
     */
    @GetMapping(value = "/write")
//    @Secured(value = "EMPLOYEE")
//    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String write() {
        return "redirect:/claim/policy";
    }

    /**
     * @api {GET} /claim/writeClaim write claim
     * @apiVersion 1.0.0
     * @apiName get write page
     * @apiGroup claim
     * @apiDescription the interface of accessing write page
     * @apiParam serialNo the serialNo of policy
     * @apiParamExample {json}
     * Request-Example 1:
     * {
     * "serialNo":86437509
     * }
     * @apiSuccess success true
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     * "error": "UserNotFound"
     * }
     */
    @GetMapping("/writeClaim")
    public String writeClaim(@RequestParam("serialNo") Long serialNo, Authentication authentication, Model model) {
        Policy policy = securityDataService.getPolicyById(serialNo);
        UserInfo customer = securityDataService.getUserByEmailAddress(authentication.getName());
        model.addAttribute("policy", policy);
        model.addAttribute("customer", customer);
        return "claim/write";
    }

    @GetMapping("claims")
    public String getAllClaims(Model model, Authentication authentication) {
        UserInfo customer = securityDataService.getUserByEmailAddress(authentication.getName());
        List<ClaimInfo> claims = securityDataService.getAllClaimsByCustomerId(customer.getId());
        model.addAttribute("claims", claims);
        return "claim/claims";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, String> handleFileUpload(HttpServletRequest request, Authentication authentication) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, String> result = new HashMap<>();
        result.put("success", "false");
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        String serialNo = request.getParameter("id");
        System.out.println(serialNo);
        MultipartFile file = null;
        File directory = new File("");
        try {
            System.out.println(directory.getCanonicalPath());//获取标准的路径
            System.out.println(directory.getAbsolutePath());//获取绝对路径
        } catch (IOException e) {
        }
        UserInfo customer = securityDataService.getUserByEmailAddress(authentication.getName());
        for (int i = 0; i < files.size(); ++i) {

            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    String fileName = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().replace(" ", "");
                    if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".gif")) {
                        result.put("msg", "Upload fail, you need to upload jpg, png or gif file");
                        return result;
                    }
                    System.out.println(fileName);
                    if (!isValidFileName(fileName)) {
                        result.put("msg", "file name is not valid");
                        return result;
                    }
                    String pathName = "../images/" + serialNo + "/" + fileName;
                    String destPath = directory.getAbsolutePath() + "/src/main/resources/static/images/"
                            + serialNo + "/" + fileName;
                    File dest = new File(destPath);
                    if (dest.exists()) {
//                        System.out.println("the file has uploaded");
                        result.put("msg", "the file has uploaded");
                        return result;
                    }
                    if (!dest.getParentFile().exists()) {
                        boolean temp = dest.getParentFile().mkdirs();
                        if (!temp) {
//                            System.out.println("fail to create directory");
                            result.put("msg", "fail to create directory");
                            return result;
                        }
                    }

                    file.transferTo(dest);
                    securityDataService.saveAndUpdateFile(new FileManager(null, pathName, Long.parseLong(serialNo)));
                    System.out.println("transfer success");
                } catch (Exception e) {
                    result.put("msg", "You failed to upload " + i + " => " + e.getMessage());
                    return result;
                }
            } else {
                result.put("msg", "You failed to upload " + i + " because the file was empty.");
                return result;
            }
        }
        result.replace("success", "true");
        result.put("msg", "upload successful");
        return result;
    }

    /**
     * @api {GET} /claim/writeClaim get write page
     * @apiVersion 1.0.0
     * @apiName get write page
     * @apiGroup claim
     * @apiDescription the interface of accessing write page
     * @apiParam serialNo the serialNo of policy
     * @apiParamExample {json}
     * Request-Example 1:
     * {
     * "serialNo":86437509
     * }
     * @apiSuccess success true
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     * "error": "UserNotFound"
     * }
     */
    @PostMapping("/finish")
    public String create(@Valid @ModelAttribute("write") WriteInfo writeInfo, BindingResult bindingResult, Model model,
                         Authentication authentication) {
        // TODO: justify the identity of user
        if (bindingResult.hasErrors()) {
            model.addAttribute("write", writeInfo);
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "claim/write";
        }
//        System.out.println(writeInfo);
        UserInfo userInfo;
        if (authentication != null) {
            userInfo = securityDataService.getUserByEmailAddress(authentication.getName());
        } else {
            // TODO: remove, for post testing
            userInfo = securityDataService.getUserByEmailAddress(writeInfo.getEmailAddress());
        }
//        System.out.println(userInfo);
        ClaimInfo claimInfo = new ClaimInfo(writeInfo.getSerialNo(), userInfo.getId(), writeInfo.getBillingAddress(),
                writeInfo.getFlightNo(), writeInfo.getLuggageType(), 0L, writeInfo.getDetails(), new Date(), null);
        System.out.println(claimInfo);
        claimInfo.setSubmitDate(new Date());
        securityDataService.saveAndUpdateClaim(claimInfo);
        model.addAttribute("customer", userInfo);
        return "claim/finish";
    }

    /**
     * @api {GET} /claim/details details of claim
     * @apiVersion 1.0.0
     * @apiName get details of claim
     * @apiGroup claim
     * @apiDescription the interface of get details of claim
     * @apiParam serialNo the serialNo of claim
     * @apiParamExample {json}
     * Request-Example 1:
     * {
     * "serialNo":86437509
     * }
     * @apiSuccess success true
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     * "error": "UserNotFound"
     * }
     */
    @GetMapping("/details")
    public String claimDetail(@RequestParam("serialNo") Long serialNo, Authentication authentication, Model model) {
        ClaimInfo claimInfo = securityDataService.getClaimById(serialNo);
        UserInfo userInfo = securityDataService.getUserById(claimInfo.getCustomerId());
        WriteInfo writeInfo = new WriteInfo(userInfo.getFirstName(), userInfo.getLastName(), userInfo.getPassport(),
                claimInfo.getSerialNo(), userInfo.getPhoneNumber(), userInfo.getEmailAddress(), claimInfo.getBillingAddress(),
                claimInfo.getFlightNo(), claimInfo.getLostLuggage(), claimInfo.getDetails());
        List<FileManager> files = securityDataService.getFileBySerialNo(serialNo);
        model.addAttribute("customerId", claimInfo.getCustomerId());
        model.addAttribute("write", writeInfo);
        model.addAttribute("result", new Result());
        model.addAttribute("files", files);
        return "employee/claimdetail";
    }

    /**
     * @api {POST} /claim/result deal with claim
     * @apiVersion 1.0.0
     * @apiName deal with claim
     * @apiGroup claim
     * @apiDescription the interface of deal with claim in employee end
     * @apiParam feedback the result of dealing with claim
     * @apiParam reason the reason for the result
     * @apiParam emailAddress the email address of customer
     * @apiParamExample {json}
     * Request-Example 1:
     * {
     * "feedback":"Approved",
     * "reason":"reasonable to claim",
     * "emailAddress":"temp@test.com"
     * }
     * @apiSuccess success true
     */
    @PostMapping("/result")
    public String sendResult(@ModelAttribute("result") Result result, Authentication authentication) {
        System.out.println(result);
        ClaimInfo claimInfo = securityDataService.getClaimById(result.getSerialNo());
        String feedback = "We have received your application.\n";
        if (result.getReason() != null) {
            feedback += "The feedback is: " + result.getReason() + "\n";
        }
        switch (result.getFeedback()) {
            case "1":
                claimInfo.setResult("Approved");
                feedback += "Congratulation!Your claim is accepted.";
                break;
            case "2":
                claimInfo.setResult("Rejected");
                feedback += "Unfortunately, your claim can not be accepted by our company.If you have any question, " +
                        "You can call our customer service hot line or send email to us.";
                break;
            case "3":
                claimInfo.setResult("To be confirmed");
                feedback += "Please give us more details about your claim.Thank you very much.";
                break;
            default:
                break;
        }
        try {
            emailSender(result, feedback);
            securityDataService.saveAndUpdateClaim(claimInfo);
            UserInfo employeeDB = securityDataService.getUserByEmailAddress(authentication.getName());
            claimInfo.setEmployeeId(employeeDB.getId());
            securityDataService.saveAndUpdateClaim(claimInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/employee/employee";
    }

    public void emailSender(Result result, String feedback) throws IOException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(result.getEmail());
        mailMessage.setSubject("We have settled your claim");
        mailMessage.setFrom("771281557@qq.com");
        mailMessage.setText(feedback);
        mailSender.send(mailMessage);
    }

}
