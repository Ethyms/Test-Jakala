package com.jakala.test.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jakala.test.model.Contract;
import com.jakala.test.model.ContractRepository;
import com.jakala.test.model.User;
import com.jakala.test.model.UserRepository;


@RestController
@RequestMapping("/jakala")
public class Controller 
{
	
	@Autowired
	ContractRepository ContractRepo;
	
	@Autowired
	UserRepository UserRepo;
	
	@GetMapping("/userlist")
	public Iterable<User> findAllUsers()
	{
		return UserRepo.findAll();
	}
	
	@PostMapping("/{id}/newcontract/{type}")
	public ResponseEntity<Contract> newcontract
	(@PathVariable("id") int id, @PathVariable String type)	
	{
		Optional <User> u = UserRepo.findById(id);
		
		Contract c = new Contract ();
		
		c.setContractType(type);
		c.setUser(u.get());
		
		if(!c.isValid())
			return new ResponseEntity<Contract>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Contract> (ContractRepo.save(c), HttpStatus.OK);
	}
	
	//filter?contractType=&userType=private user&userName=Daniele
	@GetMapping("/filter")
	public ResponseEntity<List<Contract>> filter
	(
		@RequestParam(name = "createdOn", required = false) LocalDate createdOn,
		@RequestParam(name = "contractType", required = false) String contractType,
		@RequestParam(name = "userType", required = false) String userType,
		@RequestParam(name = "userName", required = false) String userName
	)	
	{
		
        List<Contract> allContacts = (List<Contract>) ContractRepo.findAll();
        List<Contract> allUser = (List<Contract>) ContractRepo.findAll();
        
        List<Contract> res = new ArrayList<>();
        
    	for(Contract c: allContacts)
    		if (
    				(createdOn!= null && c.getCreatedOn().equals(createdOn)) ||
    				(contractType!= null && c.getContractType().equals(contractType)) ||
    				(userType!= null && c.getUser().getUserType().equals(userType)) ||
    				(userName!= null && c.getUser().getName().equals(userName))
    			)
    			res.add(c);
        
        return new ResponseEntity<List<Contract>>(res, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/user/{id}")
	public ResponseEntity<List<Contract>> findByid(@PathVariable("id") int id)
	{
		
		List<Contract> res = ContractRepo.findByUser_id(id);
		if(res.isEmpty())
			return new ResponseEntity<List<Contract>>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<List<Contract>>(res, HttpStatus.OK);
		
	}
	
	
}
