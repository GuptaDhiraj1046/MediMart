package com.softpro.MediMart.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softpro.MediMart.Dao.AdminLoginDao;
import com.softpro.MediMart.Dao.EnquiryDao;
import com.softpro.MediMart.Dao.RegistrationDao;
import com.softpro.MediMart.model.AdminLogin;
import com.softpro.MediMart.model.Enquiry;
import com.softpro.MediMart.model.Registration;
import com.softpro.MediMart.services.AdminLoginRepository;
import com.softpro.MediMart.services.EnquiryRepository;
import com.softpro.MediMart.services.RegistrationRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MainController {

	@Autowired
	EnquiryRepository erepo;
	@Autowired
	RegistrationRepository rrepo;
	@Autowired
	private SendEmailService emailService;
	
	@Autowired
	AdminLoginRepository adrepo;

	@GetMapping("/home")
	public String ShowIndex() {
		return "index";
	}

	@GetMapping("/aboutus")
	public String ShowAboutUs() {
		return "aboutus";
	}

	@GetMapping("/registration")
	public String ShowRegistration(Model model) {
		RegistrationDao registrationDao = new RegistrationDao();
		model.addAttribute("registrationDao", registrationDao);
		return "registration";
	}

	@PostMapping("/registration")
	public String CustomerRegistration(@Valid @ModelAttribute RegistrationDao registrationDao, BindingResult result, RedirectAttributes redirectAttributes)
	{
		if(result.hasErrors())
		{
			return "registration";
		}
		
		try {
			MultipartFile image = registrationDao.getProfilepic();
			if(image==null || image.isEmpty())
			{
				redirectAttributes.addFlashAttribute("message", "please select an image");
				return "redirect:/registration";
			}
			String storageFileName = new Date().getTime() +"-"+image.getOriginalFilename();
			String UploadDir = "public/profiles/";
			Path uploadPath = Paths.get(UploadDir);
			
			if(!Files.exists(uploadPath))
			{
				Files.createDirectories(uploadPath);
			}
			try(InputStream inputStream = image.getInputStream())
			{
				Files.copy(inputStream, uploadPath.resolve(storageFileName), StandardCopyOption.REPLACE_EXISTING);
			}
			
			Registration registration = new Registration();
			registration.setName(registrationDao.getName());
			registration.setGender(registrationDao.getGender());
			registration.setDob(registrationDao.getDob());
			registration.setContactno(registrationDao.getContactno());
			registration.setEmail(registrationDao.getEmail());
			registration.setPassword(registrationDao.getPassword());
			registration.setAddress(registrationDao.getAddress());
			registration.setProfilepic(storageFileName);
			registration.setRegdate(new Date()+"");
			rrepo.save(registration);
			//seding mail
			String customerMail = registrationDao.getEmail();
			String customerName = registrationDao.getName();
			String customerPassword = registrationDao.getPassword();
			emailService.SendRegistrationEmail(customerMail, customerName,customerPassword);
			
			redirectAttributes.addFlashAttribute("message", "Registration Successful");
			return "redirect:/customerlogin";
			
		} catch (Exception ex) {
			return "redirect:/registration";
		}
	}

	@GetMapping("/customerlogin")
	public String ShowCustomerLogin() {
		return "customerlogin";
	}
	
	@PostMapping("/customerlogin")
	public String CustomerLogin(@ModelAttribute RegistrationDao registrationDao, HttpSession session, RedirectAttributes redirectAttributes)
	{
		try {
			@SuppressWarnings("deprecation")
			Registration reg = rrepo.getById(registrationDao.getEmail());
			if(reg.getPassword().equals(registrationDao.getPassword()))
			{
				session.setAttribute("userid", registrationDao.getEmail());
				return "redirect:/customer/home";
			}
			else
			{
				redirectAttributes.addFlashAttribute("message", "Invalid User");
			}
					
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("message", "User Does not exits");
		}
		return "redirect:/customerlogin";
	}
	
	//admin login code
	@GetMapping("/adminlogin")
	public String ShowAdminLogin(Model model)
	{
		AdminLoginDao adminLoginDao = new AdminLoginDao();
		model.addAttribute("adminLoginDao", adminLoginDao);
		return "adminlogin";
	}
	
	@PostMapping("/adminlogin")
	public String AdminSignIn(@ModelAttribute AdminLoginDao adminLoginDao, HttpSession session, RedirectAttributes redirectAttributes)
	{
		try {
			@SuppressWarnings("deprecation")
			AdminLogin adminLogin = adrepo.getById(adminLoginDao.getUserid());
			if(adminLogin.getPassword().equals(adminLoginDao.getPassword()))
			{
				session.setAttribute("userid", adminLogin.getUserid());
				return "redirect:/admin/home";
			}
			
			
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("message", "User Does not exists");
		}
		
		return "redirect:/adminlogin";
	}

	
	
	@GetMapping("/contactus")
	public String ShowContactUs(Model model) {
		EnquiryDao enquiryDao = new EnquiryDao();
		model.addAttribute("enquiryDao", enquiryDao);
		return "contactus";
	}

	@PostMapping("/contactus")
	public String SubmitEnquiry(@Valid @ModelAttribute EnquiryDao enquiryDao, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "contactus";
		}

		Enquiry enquiry = new Enquiry();
		enquiry.setName(enquiryDao.getName());
		enquiry.setContactno(enquiryDao.getContactno());
		enquiry.setEmail(enquiryDao.getEmail());
		enquiry.setSubject(enquiryDao.getSubject());
		enquiry.setMessage(enquiryDao.getMessage());
		enquiry.setPosteddate(new Date() + "");
		erepo.save(enquiry);
		redirectAttributes.addFlashAttribute("message", "Enquiry is saved sucessfully");
		return "redirect:/contactus";
	}

}
