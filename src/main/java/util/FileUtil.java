package util;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
public class FileUtil {
		public static void upload(HttpServletRequest request,String path, int categoryId)
	    {
	        //1、将当前上下文初始化给CommonMutipartResolver
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
	        //2、检查Form中encType是否为multipart/form-data
	        if(multipartResolver.isMultipart(request))
	        {
	            //将request转为MultipartHttpServletRequest
	            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	            //获取迭代器遍历multipartRequest里的文件名
	            Iterator<String> iter = multipartRequest.getFileNames();
	            while(iter.hasNext())
	            {
	                //获取文件并向下遍历
	                MultipartFile file = multipartRequest.getFile(iter.next().toString());
	                if(file != null)
	                {
	                    //获取文件类型，即后缀名
	                    String str = file.getOriginalFilename();
	                    String suffix = str.substring(str.lastIndexOf("."));
	                    
	                    
	                    //拼接文件绝对路径
	                    String filePath = path + categoryId + suffix;

	                    try
	                    {
	                        file.transferTo(new File(filePath));
	                    } catch (IllegalStateException | IOException e)
	                    {
	                        // TODO Auto-generated catch block
	                        e.printStackTrace();
	                    }
	                }
	                return;
	            }
	        }
	        return;
	    }

	
}
