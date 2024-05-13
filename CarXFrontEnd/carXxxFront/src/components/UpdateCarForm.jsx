import React, { useState } from 'react';
import { updateCar } from '../services/CarService';
import { useParams, useNavigate, Link } from 'react-router-dom';

const UpdateCarForm = ({ currentCarData }) => {
  const [car, setCar] = useState(currentCarData || {});
  const [error, setError] = useState('');
  const { carId } = useParams();
  const navigate = useNavigate();

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setCar((prevCar) => ({ ...prevCar, [name]: value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const token = localStorage.getItem('accessToken'); 

    if (!carId) {
      setError('Trūksta automobilio ID.');
      return;
    }

    if (!token) {
      setError('Trūksta autentifikacijos rakto.');
      return;
    }

    updateCar(carId, car, token)
      .then((response) => {
        alert('Automobilis sėkmingai atnaujintas.'); 
        navigate('/home'); 
      })
      .catch((error) => {
        const errorMessage = error.response ? error.response.data.message : error.message;
        setError(`Nepavyko atnaujinti automobilio informacijos. Klaida: ${errorMessage}`);
        console.error('Klaida:', error);
      });
  };

  return (
    <div className='main-container-home'>
      {error && <div>Klaida: {error}</div>}
      <form className='form-row-home' onSubmit={handleSubmit}>
        <div>
          <label>MAKE:</label>
          <input
            name="make"
            value={car.make || ''}
            onChange={handleInputChange}
          />
          <label>MODEL:</label>
          <input
            name="model"
            value={car.model || ''}
            onChange={handleInputChange}
          />
          <label>PLATENUMBER:</label>
          <input
            name="plateNumber"
            value={car.plateNumber || ''}
            onChange={handleInputChange}
          />
        </div>
        <button type="submit">Update</button>
        <Link className='link' to="/mycars">My Cars</Link>
      </form>
    </div>
  );
};

export default UpdateCarForm;
