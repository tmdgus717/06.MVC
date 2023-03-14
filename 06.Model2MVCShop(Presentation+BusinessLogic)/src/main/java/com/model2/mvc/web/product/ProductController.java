package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;

@Controller
public class ProductController {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//생성자 없이 생성 reflection api!!
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit; //3
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize; //5
	
	@RequestMapping("/addProduct.do")
	public String addProduct(@ModelAttribute("product") Product product) throws Exception{
		productService.addProduct(product);
		return "redirect:/product/readProduct.jsp";
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct(@RequestParam("prodNo") String prodNo, Model model ) throws Exception{
		int prodNoInt = Integer.parseInt(prodNo);
		
		Product product = productService.getProduct(prodNoInt);
		model.addAttribute("product", product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView( @RequestParam("prodNo") String prodNo,
																@RequestParam("menu") String menu, Model model ) throws Exception{

		System.out.println("/updateProductView.do");
	
		int prodNoInt = Integer.parseInt(prodNo);
		Product product = productService.getProduct(prodNoInt);
		
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct( @ModelAttribute("product") Product product , HttpSession session) throws Exception{

		System.out.println("/updateProduct.do");
		//Business Logic
		productService.updateProduct(product);
		session.setAttribute("product", product);
		return "redirect:/getProduct.do?prodNo="+product.getProdNo();
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		String menu= request.getParameter("menu");
		return "forward:/product/listProduct.jsp?menu="+menu;
	}
}//end ProductController class
