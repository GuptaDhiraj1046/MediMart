package com.softpro.MediMart.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softpro.MediMart.Dao.ResponseDao;
import com.softpro.MediMart.model.Product;
import com.softpro.MediMart.model.Registration;
import com.softpro.MediMart.model.Response;
import com.softpro.MediMart.services.ProductRepository;
import com.softpro.MediMart.services.RegistrationRepository;
import com.softpro.MediMart.services.ResponseRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	RegistrationRepository rrepo;
	
	@Autowired
	ResponseRepository resrepo;
	@Autowired
	ProductRepository prepo;
	
	@GetMapping("/home")
	public String ShowCustomerDashboard(HttpSession session, Model model, HttpServletResponse httpServletResponse)
	{
		try {
			if (session.getAttribute("userid")!=null) {
				//model.addAttribute("userid", session.getAttribute("userid").toString());
				@SuppressWarnings("deprecation")
				Registration reg = rrepo.getById(session.getAttribute("userid").toString());
				model.addAttribute("name", reg.getName());
				model.addAttribute("reg", reg);
				return "/customer/customerdashboard";
			}
			else {
				return "redirect:/customerlogin";
			}
		} catch (Exception e) {
			return "redirect:/customerlogin";
		}
		
	}
	
	@GetMapping("/viewproducts")
	public String ShowViewProducts(HttpSession session, Model model, HttpServletResponse response)
	{
		if (session.getAttribute("userid")!=null) {
			model.addAttribute("userid", session.getAttribute("userid").toString());
			
			List<Product> plist = prepo.findAll();
			model.addAttribute("plist", plist);
			return "/customer/viewproducts";
		}
		else {
			return "redirect:/customerlogin";
		}
		
		
	}
	
	@GetMapping("/yourcart")
	public String ShowYourCart(HttpSession session, Model model, HttpServletResponse response)
	{
		if (session.getAttribute("userid")!=null) {
			model.addAttribute("userid", session.getAttribute("userid").toString());
			return "/customer/customercart";
		}
		else {
			return "redirect:/customerlogin";
		}
		
	}
	
	@GetMapping("/response")
	public String ShowResponse(HttpSession session, Model model, HttpServletResponse response)
	{
		if (session.getAttribute("userid")!=null) {
			model.addAttribute("userid", session.getAttribute("userid").toString());
			return "/customer/giveresponse";
		}
		else
		{
			return "redirect:/customerlogin";
		}	
	}
	
	@PostMapping("/response")
	public String SubmitResponse(HttpSession session, Model model, @ModelAttribute ResponseDao responseDao, RedirectAttributes redirectAttributes, HttpServletResponse response)
	{
		try {
			if(session.getAttribute("userid")!=null)
			{
				@SuppressWarnings("deprecation")
				Registration reg = rrepo.getById(session.getAttribute("userid").toString());
				model.addAttribute("name", reg.getName());
				Response res= new Response();
				res.setName(reg.getName());
				res.setContactno(reg.getContactno());
				res.setCustid(reg.getEmail());
				res.setResponsetype(responseDao.getResponsetype());
				res.setSubject(responseDao.getSubject());
				res.setMessage(responseDao.getMessage());
				res.setResdate(new Date()+"");
				resrepo.save(res);
				redirectAttributes.addFlashAttribute("message", "response submitted succesfully");
				return "redirect:/customer/response";
			}
			else {
				return "redirect:/customerlogin";
			}
				
		} catch (Exception e) {
			return "redirect:/customerlogin";
		}		
	}
	
	@GetMapping("/updateprofile")
	public String ShowUpdateProfile(HttpSession session, Model model, HttpServletResponse response)
	{
		if (session.getAttribute("userid")!=null) {
			model.addAttribute("userid", session.getAttribute("userid").toString());
			return "/customer/updateprofile";
		}
		else {
			return "redirect:/customerlogin";
		}
		
	}
	
	@GetMapping("/changepassword")
	public String ShowChangePassword(HttpSession session, Model model, HttpServletResponse response)
	{
		if (session.getAttribute("userid")!=null) {
			model.addAttribute("userid", session.getAttribute("userid").toString());
			return "/customer/changepassword";
		}
		else {
			return "redirect:/customerlogin";
		}		
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping("/changepassword")
	public String ChangePassword(HttpSession session, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response)
	{
		try {
			if(session.getAttribute("userid")!=null)
			{
				@SuppressWarnings({ })
				Registration reg = rrepo.getById(session.getAttribute("userid").toString());
				model.addAttribute("name", reg.getName());
				String oldpass = request.getParameter("oldpass");
				String newpass = request.getParameter("newpass");
				String confirmpass = request.getParameter("confirmpass");
				if(!newpass.equals(confirmpass))
				{
					redirectAttributes.addFlashAttribute("message", "new password and confirm password not matched");
					return  "redirect:/customer/changepassword";
				}
				if(!oldpass.equals(reg.getPassword()))
				{
					redirectAttributes.addFlashAttribute("message", "old password is invalid");
					return "redirect:/customer/changepassword";
				}
				reg.setPassword(confirmpass);
				rrepo.save(reg);
				return "redirect:/customer/customerlogout";
			}
			else {
				return "redirect:/customerlogin";
			}
		} catch (Exception e) {
			return "redirect:/customerlogin";
		}
		
	}
	
	
	
	@GetMapping("/customerlogout")
	public String CustomerLogout(HttpSession session)
	{
		session.invalidate();
		return "redirect:/customerlogin";
	}
	
}
