import React, { useEffect, useState } from 'react';

function WeatherStation() {
    const [stoteles, setStoteles] = useState([]);
    const [pasirinktasIrenginys, setPasirinktasIrenginys] = useState('');

    useEffect(() => {
        fetch('https://eismoinfo.lt/weather-conditions-service')
            .then(response => response.json())
            .then(data => {
                setStoteles(data);
            })
            .catch(error => console.error('Klaida gaunant duomenis:', error));
    }, []);

    const handleIrenginioKeitimas = (e) => {
        setPasirinktasIrenginys(e.target.value);
    };

    const pasirinktosStotelesDuomenys = stoteles.find(stotele => stotele.irenginys === pasirinktasIrenginys);

    return (
        <div>
            <h1 className='stasion-info' >Would you like to know the weather on the road? Just a few button clicks and your travels will become safer.</h1>
            <select value={pasirinktasIrenginys} onChange={handleIrenginioKeitimas}>
                <option value="">Select...</option>
                {stoteles.map(stotele => (
                    <option key={stotele.id} value={stotele.irenginys}>{stotele.irenginys}</option>
                ))}
            </select>

            {pasirinktosStotelesDuomenys && (
                <div>
                    <h2>Oras įrenginyje: {pasirinktosStotelesDuomenys.irenginys}</h2>
                    <p>Temperatūra: {pasirinktosStotelesDuomenys.oro_temperatura}°C</p>
                    <p>Dangos temperatūra: {pasirinktosStotelesDuomenys.dangos_temperatura} °C</p>
                    <p>Kelio danga: {pasirinktosStotelesDuomenys.kelio_danga}</p>
                    <p>Krituliai: {pasirinktosStotelesDuomenys.krituliu_tipas}</p>
                    <p>Vėjo greitis: {pasirinktosStotelesDuomenys.vejo_greitis_vidut} m/s</p>
                </div>
            )}
        </div>
    );
}

export default WeatherStation;
