import axios from "axios";
import React, {useState} from 'react';
import api from "../api/api";


const authService = {

	getCsrfToken: async() => {
		try{
			const response = await api.get("http://localhost:8080/api/auth/csrf-token")
			return response.data.token;
		}catch(error){
			console.log(error.message); 
			throw error;
		}
	},



	login: async(credential) => {
		console.log("DEBUG REACHED");
		const csrfToken = await authService.getCsrfToken();
		const params = { 
			email: credential.email,
			password: credential.password
		}
		const headers = { headers: { "X-XSRF-TOKEN": csrfToken } }
		try{
			const response = await api.post("http://localhost:8080/api/auth/login", params, headers);
			return response.data;
		}catch(error){
			console.log(error.response); 
			throw error;
		}
		
	},


	logout: async() => {
		return new Promise((resolve) => {
		setTimeout(() => {
			localStorage.removeItem('mockUser');
			localStorage.removeItem('mockUser');
			resolve({
				message: 'logout'
			})

		}, 1000)
	})
	},


	checkAuth: async() => {
		return new Promise((resolve) => {
			const user = localStorage.getItem('mockUser');
			const isLoggedIn = localStorage.getItem('isLoggedIn');
			if(user && isLoggedIn){
				resolve({			
					authenticated: true,
					user: JSON.parse(user)
				})
			}else{
				resolve({			
					authenticated: false,

				})
			}

		})
	}
}
export default authService;