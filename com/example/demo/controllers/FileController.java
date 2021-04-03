package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.models.*;
import com.example.demo.repository.*;
import com.example.demo.payload.response.*;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;
//gaga
/*
 * 
 *  @Autowired
  private FileDBRepository fileDBRepository;

  public FileDB store(MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

    return fileDBRepository.save(FileDB);
  }

  public FileDB getFile(String id) {
    return fileDBRepository.findById(id).get();
  }
  
  public Stream<FileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }*/

@Controller
@CrossOrigin("*")
@RequestMapping("/api/submit")
public class FileController {

	@Autowired
	FileRepository fileRepository;

	public File store(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		File FileDB = new File(fileName, file.getContentType(), file.getBytes());

		return fileRepository.save(FileDB);
	}

	@PostMapping(value = "/upload")
	public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
		// TODO: process POST request
		String message = "";
		try {
			store(file); // save file into databse
			message = "Uploaded the file successfully : " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message)); // if save failed --> throw Error
		}
	}

	@GetMapping(value = "/files")
	public ResponseEntity<List<ResponseFile>> getListFiles() {
		List<ResponseFile> files = fileRepository.findAll().stream().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/submit/files/")
					.path(dbFile.getId()).toUriString();
			return new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping(value = "/files/{id}") // download file
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		File file = fileRepository.findById(id).get(); // find id of file and download
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
				.body(file.getData());
	}
	
	

}
