'use client';

import React, {useEffect, useState} from "react";

interface Vehicle {
    plate: string;
    name: string;
    type: string;
    dailyRate: string;
    status: string;
}

const VehicleRental: React.FC = () => {
    const [vehicles, setVehicles] = useState<Vehicle[]>([]);

    useEffect(() => {
        // Fetch vehicles from the backend API
        fetch("/api/vehicles")
            .then((response) => response.json())
            .then((data) => setVehicles(data))
            .catch((error) => console.error("Error fetching vehicles:", error));
    }, []);

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Coders24 Vehicle Rental</h1>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                {vehicles.map((vehicle) => (
                    <div key={vehicle.plate} className="border p-4 rounded shadow">
                        <h2 className="text-xl font-semibold">{vehicle.name}</h2>
                        <p>Type: {vehicle.type}</p>
                        <p>Daily Rate: ${vehicle.dailyRate}</p>
                        <p>Status: {vehicle.status}</p>
                        <button className="mt-2 px-4 py-2 bg-blue-500 text-white rounded">
                            Rent
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default VehicleRental;