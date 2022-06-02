package poly.store.service.impl;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import poly.store.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService{
@Autowired
ServletContext app;
public File save(MultipartFile file ,String folder) {
		File dir = new File(app.getRealPath("/assets/" + folder)); // lam viec voi duong dan
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		//tao ra foler neu chua ton tao
		String s = System.currentTimeMillis() + file.getOriginalFilename();
		String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
		//servlet context lam viec voi duong dan
		try {
			File savedFile = new File(dir,name); // luu folder
			file.transferTo(savedFile);
			System.out.println(savedFile.getAbsolutePath());
			return savedFile;
		} catch (Exception e) {
			throw new RuntimeException(e);
			
		}
			
}
}
