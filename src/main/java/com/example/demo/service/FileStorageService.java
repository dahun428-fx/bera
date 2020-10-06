package com.example.demo.service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.FileDownloadException;
import com.example.demo.exception.FileUploadException;
import com.example.demo.property.FileStorageProperties;


@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	private String directory;
	
	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
		}
	}
	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		fileName = System.currentTimeMillis() + fileName.replaceAll(" ", "");
		
		try {
			if(fileName.contains("..")) throw new FileUploadException("파일명에 부적합한 문자가 포함되어 있습니다 " +  fileName);
				
			Path targetLocation = this.fileStorageLocation.resolve(
					(this.getDirectory() == null) ? "": this.getDirectory() + "/" + fileName);
			
			System.out.println("targetLocation :" + targetLocation);
			System.out.println("category : " + this.getDirectory());
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
		} catch (Exception e) {
			throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오", e);
		}
	}
	public Resource loadFileAsResource(String fileName) {
		
		try {
			
			Path filePath =  this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			} else {
				throw new FileDownloadException(fileName + "파일을 찾을 수 없습니다.");
			}
			
		} catch (MalformedURLException e) {
			throw new FileDownloadException(fileName + "파일을 찾을 수 없습니다.", e);
		}
		
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
}
