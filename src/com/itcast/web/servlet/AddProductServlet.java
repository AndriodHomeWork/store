package com.itcast.web.servlet; 

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.itcast.bean.Category;
import com.itcast.bean.Product;
import com.itcast.service.ProductService;
import com.itcast.service.impl.ProductServiceImpl;
import com.itcast.utils.UUIDUtils;
import com.itcast.utils.UploadUtils;


public class AddProductServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//整体思路: 1, 获得请求参数, 把请求参数封装成Product对象,最终要保存到数据库  2. 保存商品的图片到服务器
		
		/*String pname = request.getParameter("pname");
		String is_hot = request.getParameter("is_hot");
		String market_price = request.getParameter("market_price");
		String shop_price = request.getParameter("shop_price");
		String pimage = request.getParameter("pimage");
		String cid = request.getParameter("cid");
		String pdesc = request.getParameter("pdesc");*/
		
		try {
			//0, 创建Product
			Product product = new Product();
			Map<String, Object> map = new HashMap<String, Object>();
			
			//1. 创建磁盘文件项工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//2. 创建文件核心上传对象
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			//3. 解析request, 获得文件项集合
			List<FileItem> list = fileUpload.parseRequest(request);
			//4, 遍历文件项集合
				/**(添加商品的表单中,每一个文本框都是一个fileItem,所以整个表示是一个fileItem集合)
				 *   
				 ***/
			
			for (FileItem fileItem : list) {
				if(fileItem.isFormField()){
					/**不是file,只需要获得value,封装到Product对象**/
					//获得value
					String value = fileItem.getString("utf-8");
					//获得表单里面name的属性值(javaBean属性)
					String fieldName = fileItem.getFieldName();
					map.put(fieldName, value);
				}else{
/**是File, 1.把文件的路径(products/8/9/06F518F53D314209ACD0A1C419E28168.jpg)封装 到Product对象  2. 保存文件到服务器**/
					
					//-----------1.把文件的路径(products/8/9/06F518F53D314209ACD0A1C419E28168.jpg)封装 到Product对象------------------
					//a 获得文件名 
					String fileName = fileItem.getName();
					//b 去掉盘符  a.jpg
					String realName = UploadUtils.getRealName(fileName);
					//c 获得uuid名字
					String uuidName = UploadUtils.getUUIDName(realName);
					//d 获得两层目录  /A/B
					String dir = UploadUtils.getDir();
					//e,获得表单里面name的属性值(javaBean属性)
					String fieldName = fileItem.getFieldName();
					map.put(fieldName, "products"+dir+"/"+uuidName);

					//-----------2. 保存文件到服务器-------------------
					/** 保存到服务器 其实就是将图片保存到tomcat里面的webapps里面 分级目录保存* */
					//a 获得文件(流)
					InputStream is = fileItem.getInputStream();
					
					//b,动态获得products绝对路径   E:\worksoft\tomcat\apache-tomcat-7.0.52_28_32\webapps\store_28\products
					String realPath = request.getServletContext().getRealPath("/products");
					//c 在products里面成两层目录
					File fileDir = new File(realPath, dir);
					if(!fileDir.exists()){
						fileDir.mkdirs();
				}
					//d, 根据文件夹和文件创建输出流
					OutputStream os =  new FileOutputStream(new File(fileDir, uuidName));
					//e, 删除临时文件
					fileItem.delete();
					
					IOUtils.copy(is, os);
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
				
			}
			
			}
			
			try {
				//5, 把map的数据封装到product对象
				BeanUtils.populate(product, map);
				//5.1 手到封装pid, pdate, category
				product.setPid(UUIDUtils.getId());
				product.setPdate(new Date());//cid
				Category category = new Category();
				category.setCid((String)map.get("cid"));
				product.setCategory(category);
				
				//6. 调用业务
				ProductService productService = new ProductServiceImpl();
				productService.add(product);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			//7. 再查询展示
			response.sendRedirect(request.getContextPath()+"/adminProductServlet?method=findAll");
			} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
