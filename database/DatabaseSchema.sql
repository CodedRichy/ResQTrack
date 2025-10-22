-- Database Schema for ResQTrack Disaster Management System
-- Database: disaster_management_system

CREATE DATABASE IF NOT EXISTS disaster_management_system;
USE disaster_management_system;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'volunteer', 'citizen') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Shelters table
CREATE TABLE IF NOT EXISTS shelters (
    shelter_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    capacity INT NOT NULL,
    available_capacity INT NOT NULL,
    food INT DEFAULT 0,
    water INT DEFAULT 0,
    medicine INT DEFAULT 0,
    location VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tasks table
CREATE TABLE IF NOT EXISTS tasks (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    description TEXT NOT NULL,
    assigned_user_id INT,
    status ENUM('Pending', 'In Progress', 'Completed', 'Cancelled') DEFAULT 'Pending',
    location VARCHAR(200),
    priority ENUM('Low', 'Medium', 'High', 'Critical') DEFAULT 'Medium',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (assigned_user_id) REFERENCES users(user_id) ON DELETE SET NULL
);

-- SOS Alerts table
CREATE TABLE IF NOT EXISTS sos_alerts (
    alert_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    location VARCHAR(200) NOT NULL,
    message TEXT,
    status ENUM('Active', 'Resolved', 'Cancelled') DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Reports table
CREATE TABLE IF NOT EXISTS reports (
    report_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    description TEXT NOT NULL,
    casualties INT DEFAULT 0,
    damages TEXT,
    location VARCHAR(200),
    status ENUM('Pending', 'Under Review', 'Resolved') DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Hazards table
CREATE TABLE IF NOT EXISTS hazards (
    hazard_id INT PRIMARY KEY AUTO_INCREMENT,
    description TEXT NOT NULL,
    location VARCHAR(200) NOT NULL,
    status ENUM('Active', 'Resolved', 'False Alarm') DEFAULT 'Active',
    severity ENUM('Low', 'Medium', 'High', 'Critical') DEFAULT 'Medium',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Public Info table
CREATE TABLE IF NOT EXISTS public_info (
    info_id INT PRIMARY KEY AUTO_INCREMENT,
    type ENUM('Alert', 'Guideline', 'Shelter', 'Contact', 'Update') NOT NULL,
    description TEXT NOT NULL,
    status ENUM('Active', 'Archived', 'Expired') DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'admin'),
('volunteer1', 'vol123', 'volunteer'),
('volunteer2', 'vol456', 'volunteer'),
('citizen1', 'cit123', 'citizen'),
('citizen2', 'cit456', 'citizen'),
('citizen3', 'cit789', 'citizen');

INSERT INTO shelters (name, capacity, available_capacity, food, water, medicine, location) VALUES
('Government School', 500, 450, 100, 200, 50, 'Downtown Area'),
('Community Center', 300, 300, 75, 150, 30, 'Suburb Area'),
('Sports Complex', 800, 750, 200, 400, 100, 'Sports District'),
('City Hall', 200, 180, 50, 100, 25, 'City Center'),
('High School Gym', 400, 350, 80, 160, 40, 'Education District'),
('Church Basement', 150, 120, 30, 60, 15, 'Religious Quarter');

INSERT INTO tasks (description, assigned_user_id, location, status, priority) VALUES
('Deliver emergency supplies to Government School', 2, 'Downtown Area', 'Pending', 'High'),
('Assess damage at Sports Complex', 3, 'Sports District', 'In Progress', 'Medium'),
('Set up temporary medical station', 2, 'City Center', 'Completed', 'Critical'),
('Coordinate with local authorities', 1, 'City Hall', 'Pending', 'High'),
('Distribute food and water', 3, 'Suburb Area', 'In Progress', 'Medium'),
('Evacuate residents from flood zone', 2, 'Riverside District', 'Pending', 'Critical'),
('Check shelter capacity', 3, 'Education District', 'Completed', 'Low'),
('Update emergency contact list', 1, 'City Center', 'Pending', 'Medium');

INSERT INTO sos_alerts (user_id, location, message, status) VALUES
(4, '123 Maple Street', 'Building collapse, assistance needed immediately', 'Active'),
(5, 'Oakwood Park', 'Medical emergency, elderly person injured', 'Resolved'),
(6, 'Riverside District', 'Flooding in progress, need evacuation help', 'Active'),
(4, 'Main Street Bridge', 'Traffic accident with injuries', 'Active'),
(5, 'Shopping Mall', 'Fire alarm triggered, need verification', 'Resolved'),
(6, 'Industrial Area', 'Chemical spill reported', 'Active');

INSERT INTO reports (user_id, description, casualties, damages, location, status) VALUES
(4, 'Flood in city area', 5, 'Houses damaged, roads blocked', 'Riverside District', 'Pending'),
(5, 'Earthquake in town', 10, 'Buildings collapsed, infrastructure damaged', 'Downtown Area', 'Under Review'),
(6, 'Fire outbreak in warehouse', 2, 'Warehouse destroyed, smoke affecting nearby areas', 'Industrial Area', 'Resolved'),
(4, 'Gas leak reported', 0, 'Evacuation of 200 people required', 'Residential Zone', 'Pending'),
(5, 'Bridge collapse', 3, 'Main bridge destroyed, traffic disrupted', 'Transportation Hub', 'Under Review'),
(6, 'Power outage affecting 1000 homes', 0, 'Electrical grid damaged', 'Suburb Area', 'Resolved');

INSERT INTO hazards (description, location, status, severity) VALUES
('Broken gas line reported', 'Main Street, Block A', 'Active', 'High'),
('Fallen tree blocking road', 'Oak Avenue', 'Active', 'Medium'),
('Power outage in sector 5', 'Industrial Area', 'Active', 'Critical'),
('Damaged water main', 'Residential Zone', 'Resolved', 'High'),
('Sinkhole formation', 'Park District', 'Active', 'Critical'),
('Chemical spill', 'Warehouse District', 'Active', 'High'),
('Bridge structural damage', 'Transportation Hub', 'Under Review', 'Critical'),
('Landslide risk', 'Mountain View Area', 'Active', 'High');

INSERT INTO public_info (type, description, status) VALUES
('Alert', 'Flood warning in coastal area', 'Active'),
('Alert', 'Evacuation order for Riverside District', 'Active'),
('Guideline', 'Keep emergency kit ready', 'Active'),
('Guideline', 'Stay indoors during chemical spill', 'Active'),
('Shelter', 'Govt School, Capacity: 500', 'Active'),
('Shelter', 'Sports Complex, Capacity: 800', 'Active'),
('Contact', 'Disaster Helpline: 108', 'Active'),
('Contact', 'Emergency Services: 911', 'Active'),
('Update', 'Power restoration in progress', 'Active'),
('Update', 'Road repairs completed on Main Street', 'Archived');

