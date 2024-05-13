import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8080/';
const REST_API_URL = 'http://localhost:8080/api';
const REST_API_AUTH_URL = 'http://localhost:8080/api/auth';

export const getCar = (carId) => axios.get(REST_API_URL + '/' + carId);


export const signUp = (signUpRequest) => {
    return axios.post(`${REST_API_AUTH_URL}/signup`, signUpRequest, {
      headers: {
        'Content-Type': 'application/json' 
      }
    });
};
export const signIn = (loginRequest) => {
    return axios.post(`${REST_API_AUTH_URL}/signin`, loginRequest, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
};

export const getCarsByOwner = (token) => {
  return axios.get(`${REST_API_URL}/mycars`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};

export const updateCar = (carId, updatedCar, token) => {
  return axios.put(`${REST_API_URL}/update/${carId}`, updatedCar, {
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  });
};

export const createCar = (carData, token) => {
  return axios.post(`${REST_API_URL}/createcar`, carData, {
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  });
};

export const deleteCar = (carId, token) => {
  return axios.delete(`${REST_API_URL}/delete/${carId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  });
};

