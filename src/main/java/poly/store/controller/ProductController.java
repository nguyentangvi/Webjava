package poly.store.controller;




import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.store.entity.Category;
import poly.store.entity.Product;
import poly.store.service.CategoryService;
import poly.store.service.ProductService;


@Controller
public class ProductController {
	@Autowired
	ProductService productservice;
	@Autowired
	CategoryService categoryService;
	
	/*
	 * @RequestMapping("/product/list") public String list(Model model
	 * , @RequestParam("cid") Optional<String> cid ) { if(cid.isPresent()) {
	 * List<Product> list = productservice.findByCategoryId(cid.get());
	 * model.addAttribute("items",list);
	 * 
	 * }else { List<Product> list = productservice.findAll();
	 * model.addAttribute("items",list); }
	 * 
	 * 
	 * return "product/list"; }
	 */
	@RequestMapping("/product/detail/{id}")
	public String detail(Model model ,@PathVariable("id")Integer id) {
		Product item = productservice.findByid(id);
		model.addAttribute("item",item);
		return "product/detail";
	}
	
	@RequestMapping("/product/list")
	public String store() {
		return "redirect:/product/list/page?p=0";
	}
	 // tìm kiếm theo tên & phân trang
    @RequestMapping(value = "/product/list/page")
    public String list(ModelMap model, @RequestParam(name = "name", required = false) String name,
            @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size
           ) {

        int currentPage = page.orElse(1);// trang hien tai
        int pageSize = size.orElse(3);// kich thuoc cua page
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
        Page<Product> resultPage = null;

        if (StringUtils.hasText(name)) {
            resultPage = productservice.findByNameContaining(name, pageable);
            model.addAttribute("name", name);
        } else {
        	
            resultPage = productservice.findAll(pageable);
        }

        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            if (totalPages > 3) {
                if (end == totalPages)
                    start = end - 3;
                else if (start == 1)
                    end = start + 3;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

        }
        model.addAttribute("shop", "co");
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("productPage", resultPage);
		
		 model.addAttribute("pitems", resultPage); 
		 List<Category> cateList=categoryService.finAll();
		 model.addAttribute("citems",cateList);
		 
		
        return "product/list";

    }
    
    
    @RequestMapping(value = "/product/list/cate")
    public String listCate(ModelMap model, @RequestParam(name = "categoryID", required = false) String categoryID,
            @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size
           ) {
        int currentPage = page.orElse(1);// trang hien tai
        int pageSize = size.orElse(3);// kich thuoc cua page
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
        Page<Product> resultPage = null;

        if (StringUtils.hasText(categoryID)) {
            resultPage = productservice.findByProductCate(categoryID, pageable);
            model.addAttribute("categoryID", categoryID);
        } else {
        	
        return "404";
        }

        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 3);
            int end = Math.min(currentPage + 3, totalPages);
            if (totalPages > 3) {
                if (end == totalPages)
                    start = end - 3;
                else if (start == 1)
                    end = start + 3;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

        }
        model.addAttribute("categorypage", categoryID);
        model.addAttribute("shop", "co");
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("productPage", resultPage);
		
		 model.addAttribute("pitems", resultPage); 
		 List<Category> cateList=categoryService.finAll();
		 model.addAttribute("citems",cateList);
		 
		
        return "product/list";

    }
	
	
}
