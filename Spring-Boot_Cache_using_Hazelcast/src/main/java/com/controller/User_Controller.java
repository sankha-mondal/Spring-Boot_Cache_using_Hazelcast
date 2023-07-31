package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.payload.ApiResponse;
import com.repository.User_Repository;
import com.service.User_Service;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user") //  http://localhost:8585/users_credentials/user

public class User_Controller {

	@Autowired
	User_Repository userRepo;

	@Autowired
	User_Service userService;

//*************************************************** : CRUD Operation : ***************************************************************** 
//=======================================================================================================================================

		//  http://localhost:8585/users_credentials/user/get_data
	
		@GetMapping("/get_data")
		public Map<String, String> getData() {
			return Map.of("Name","Sankha Subhra");
		}


		//  Retrieve Operation:-  Op:1
		//  http://localhost:8585/users_credentials/user/getAll

		@GetMapping("/getAll")
//		@Transactional(readOnly = true)
//		@Cacheable("user-cache_all")
		public List<User> getAllUser() {

			return userRepo.findAll();
		}

//=======================================================================================================================================

		//  Retrieve data by User-Email :-  Op:2
		//  http://localhost:8585/users_credentials/user/getPassByEmail/{uEmail}

		@GetMapping("/getPassByEmail/{uEmail}")
		@Transactional(readOnly = true)
		@Cacheable("user-cache_single")
		public User getPassByEmail(@PathVariable("uEmail") String uEmail) {

			User user = userRepo.findById(uEmail)
					.orElseThrow(() -> new ResourceNotFoundException("Not found User with Email = " + uEmail));

			return userRepo.findById(uEmail).get();
			// return new ResponseEntity<>(user, HttpStatus.OK);
		} 

//=======================================================================================================================================

		//  Insert Operation:-    Op:3
		//  http://localhost:8585/users_credentials/user/store
		
		//   @PostMapping("/store")
		//   public ResponseEntity<Passenger> storePassenger(@RequestBody Passenger passReq) {
		//   		Passenger _passenger = passengerRepo.save(new Passenger(passReq.getpEmail(),
		//     																passReq.getpName(),
		//     																passReq.getpPhone(),
		//     																passReq.getpPassword(),
		//															        passReq.getpRole(),
		//                                                                  passReq.getpAddress(),      
		//                                                                  passReq.getUrl(),
		//                                                                  passReq.getBooking()
		//															        ));
		//   
		//	        return new ResponseEntity<>(_passenger, HttpStatus.CREATED);
		//   }

		@PostMapping(value="store", consumes = MediaType.APPLICATION_JSON_VALUE)
		public String storePassenger(@RequestBody User user) {
			System.out.println(user.getuEmail());

			return userService.storeUser(user);
		}

//=======================================================================================================================================

		//  Update Operation:-   Op:4
		//  http://localhost:8585/users_credentials/user/update/{uEmail}

		@PutMapping("/update/{uEmail}")
		public User updateUser(@PathVariable("uEmail") String uEmail,
									@RequestBody User userReq) {

			User _user = userRepo.findById(uEmail)
					.orElseThrow(() -> new ResourceNotFoundException("Not found User with Email = " + uEmail));

					_user.setuAddress(userReq.getuAddress());
					_user.setuName(userReq.getuName());
					_user.setuPassword(userReq.getuPassword());
					_user.setuPhone(userReq.getuPhone());
					_user.setuGender(userReq.getuGender());
					_user.setuRole(userReq.getuRole());
					_user.setUrl(userReq.getUrl());
				//  _user.setBooking(userReq.getBooking());  // Don't do this bcoz it will store null at the time of Update.

			return userRepo.save(_user);
		}

//=======================================================================================================================================

		//  Delete Operation by Id:-   Op:5
		//  http://localhost:8585/users_credentials/user/delete/{uEmail}

		@DeleteMapping("/delete/{uEmail}")
		@CacheEvict("user-cache_single")
		public void deleteUser(@PathVariable("uEmail") String uEmail) {

				User user_email = userRepo.findById(uEmail)
						.orElseThrow(() -> new ResourceNotFoundException("Not found User with Email = " + uEmail));

				userRepo.deleteById(uEmail);
		}

//=======================================================================================================================================

		//  All Delete Operation:-   Op:6
		//  http://localhost:8585/users_credentials/user/deleteAll
		//  Step:4. Evict cache

		@DeleteMapping("/deleteAll")
		public ResponseEntity<HttpStatus> deleteAllUser() {

				userRepo.deleteAll();
    
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

 

//======================================================================================================================================
//**************************************************** : User Define : ***************************************************************** 
//======================================================================================================================================

		//  Retrieve Message by Email & password | Login Operation :-   Op: 7
		//  http://localhost:8585/users_credentials/user/login

		@PostMapping(value = "login")
		public String find_UserByEmailPassword(@RequestBody User user) {

				System.out.println("Controller: "+user.getuEmail());
				//Thread.sleep(3000);
				return userService.find_UserByEmailPassword(user);
		}


//=============================================================================================================================== 
//=============================================================================================================================== 

}