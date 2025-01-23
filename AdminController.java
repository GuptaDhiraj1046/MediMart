package com.softpro.MediMart.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softpro.MediMart.Dao.ProductDao;
import com.softpro.MediMart.model.AdminLogin;
import com.softpro.MediMart.model.Enquiry;
import com.softpro.MediMart.model.Product;
import com.softpro.MediMart.model.Registration;
import com.softpro.MediMart.model.Response;
import com.softpro.MediMart.services.AdminLoginRepository;
import com.softpro.MediMart.services.EnquiryRepository;
import com.softpro.MediMart.services.ProductRepository;
import com.softpro.MediMart.services.RegistrationRepository;
import com.softpro.MediMart.services.ResponseRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	RegistrationRepository arrepo;
	
	@Autowired
	EnquiryRepository aerepo;
	
	@Autowired
	ResponseRepository resrepo;
	
	@Autowired
	ProductRepository prepo;
	
	@Autowired
	AdminLoginRepository arepo;
	
	@GetMapping("/home")
	public String ShowDashboard(HttpSession session, Model model, HttpServletResponse response)
	{
		try {
			if(session.getAttribute("userid")!=null)
			{
				model.addAttribute("userid", session.getAttribute("userid").toString());
				int ccount = (int)arrepo.count();
				int ecount = (int)aerepo.count();
				int rescount = (int)resrepo.count();
				int pcount = (int)prepo.count();
				model.addAttribute("ccount", ccount);
				model.addAttribute("ecount", ecount);
				model.addAttribute("rescount", rescount);
				model.addAttribute("pcount", pcount);
				
				return "/admin/admindashboard";
			}
			else {
				return "redirect:/adminlogin";
			}
		} catch (Exception e) {
			return "/admin/admindashboard";
		}
		
	}
	
	@GetMapping("/viewcustomers")
	public String ShowViewCustomers(HttpSession session, Model model, HttpServletResponse response)
	{
		try {
			if(session.getAttribute("userid")!=null)
			{
				model.addAttribute("userid", session.getAttribute("userid").toString());
				List<Registration> clist = arrepo.findAll();
				model.addAttribute("clist", clist);
				return "admin/viewcustomers";
			}
			else {
				return "redirect:/adminlogin";
			}
		} catch (Exception e) {
			return "redirect:/adminlogin";
		}		
	}
	
	@GetMapping("/enquiry")
	public String ShowEnquiry(HttpSession session,Model model, HttpServletResponse response)
	{
		try {
			if(session.getAttribute("userid")!=null)
			{
				model.addAttribute("userid", session.getAttribute("userid").toString());
				List<Enquiry> enquiryList = aerepo.findAll();
				model.addAttribute("enquiryList", enquiryList);
				return "/admin/viewinquiry";
				
			}
			else {
				return "redirect:/adminlogin";
			}
			
		} catch (Exception e) {
			return "redirect:/adminlogin";
		}
		
	}
	
	@GetMapping("feedback")
	public String ShowFeedbacks(HttpSession session , Model model, HttpServletResponse response)
	{
		try {
			if (session.getAttribute("userid")!=null) 
			{
				model.addAttribute("userid", session.getAttribute("userid").toString());
				List<Response> fresList = resrepo.findResponseByResponsesType("feedback");
				model.addAttribute("fresList", fresList);
				return "/admin/viewfeedback";
			}
			else 
			{
				return "redirect:/adminlogin";
			}		
		} 
		catch (Exception e) 
		{
			return "redirect:/adminlogin";
		}
	}
	
	@GetMapping("/complaint")
	public String ShowComplaint(HttpSession session, Model model, HttpServletResponse response)
	{
		try {
			if (session.getAttribute("userid")!=null) {
				model.addAttribute("userid", session.getAttribute("userid").toString());
				List<Response> cresList = resrepo.findResponseByResponsesType("complaint");
				model.addAttribute("cresList", cresList);
				return "/admin/viewcomplaint";
			}
			else {
				return "redirect:/adminlogin";
			}
		} catch (Exception e) {
			return "redirect:/adminlogin";
		}
		
	}
	
		
	//add products
	@GetMapping("/addproduct")
	public String ShowAddProduct(HttpSession session, Model model, HttpServletResponse response)
	{
		try {
			if(session.getAttribute("userid")!=null)
			{
				model.addAttribute("userid", session.getAttribute("userid").toString());
				ProductDao productDao = new ProductDao();
				model.addAttribute("productDao", productDao);
				
				return "/admin/addproduct";
			}
			else {
				return "redirect:/adminlogin";
			}
			
		} catch (Exception e) {
			return "/admin/addproduct";
		}
	}
	
	@PostMapping("/addproduct")
	public String AddProducts(HttpSession session, Model model, @ModelAttribute ProductDao productDao, RedirectAttributes redirectAttributes, HttpServletResponse response ) {
		try {
			if(session.getAttribute("userid")!=null)
			{
				model.addAttribute("userid", session.getAttribute("userid").toString());
				
				MultipartFile image = productDao.getPimage();
				if(image==null || image.isEmpty())
				{
					redirectAttributes.addFlashAttribute("message", "please select an image");
					return "redirect:/admin/addproduct";
				}
				
				String storageFileName = new Date().getTime() +"-"+ image.getOriginalFilename();
				//System.out.println(storageFileName);
				String UploadDir = "public/products/";
				Path uploadPath = Paths.get(UploadDir);
				
				if(!Files.exists(uploadPath))
				{
					Files.createDirectories(uploadPath);
				}
				
				try(InputStream inputStream = image.getInputStream())
				{
					Files.copy(inputStream, Paths.get(UploadDir+storageFileName), StandardCopyOption.REPLACE_EXISTING);
				}				
				
				Product product = new Product();
				product.setPid(productDao.getPid());
				product.setPname(productDao.getPname());
				product.setPdesc(productDao.getPdesc());
				product.setPprice(productDao.getPprice());
				product.setPimage(storageFileName);
				prepo.save(product);
				redirectAttributes.addFlashAttribute("message", "Product Added Successfully");
				return "redirect:/admin/addproduct";	
			}
			else
			{
				return "redirect:/adminlogin";
			}
			
		} catch (Exception e) {
			return "redirect:/adminlogin";
		}
	}
	
	@GetMapping("/product")
	public String ShowProduct(HttpSession session, Model model, HttpServletResponse response)
	{
		try {
			if (session.getAttribute("userid")!=null) {
				model.addAttribute("userid", session.getAttribute("userid").toString());
				List<Product> plist = prepo.findAll();
				model.addAttribute("plist", plist);
				return "/admin/viewproduct";
			}
			else {
				return "redirect:/adminlogin";
			}
			
		} catch (Exception e) {
			return "redirect:/adminlogin";
		}
		
	}
	
	@GetMapping("/changepassword")
	public String ShowChangePassword(HttpSession session, Model model, HttpServletResponse response)
	{
		try {
			if (session.getAttribute("userid")!=null) {
				model.addAttribute("userid", session.getAttribute("userid").toString());
				return "/admin/changepassword";
			}
			else {
				return "redirect:/adminlogin";
			}
		} catch (Exception e) {
			return "redirect:/adminlogin";
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping("/changepassword")
	public String ChangePassword(HttpSession session, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response)
	{
		try {
			if (session.getAttribute("userid")!=null) {
				AdminLogin ad = arepo.getById(session.getAttribute("userid").toString());
				String oldpass = request.getParameter("oldpass");
				String newpass = request.getParameter("newpass");
				String confirmpass = request.getParameter("confirmpass");
				if(!newpass.equals(confirmpass))
				{
					redirectAttributes.addFlashAttribute("message", "New Pass and Confirm Password is not Matched");
					return "redirect:/admin/changepassword";
				}
				if(!oldpass.equals(ad.getPassword()))
				{
					redirectAttributes.addFlashAttribute("message", "Invalid Old Password");
					return "redirect:/admin/changepassword";
				}
				ad.setPassword(confirmpass);
				arepo.save(ad);
				return "redirect:/admin/adminlogout";
			}
			else {
				return "redirect:/adminlogin";
			}
		} catch (Exception e) {
			return "redirect:/adminlogin";
		}
		
	}
	
	@GetMapping("/adminlogout")
	public String AdminLogout(HttpSession session)
	{
		session.invalidate();
		return "redirect:/adminlogin";
	}
}
