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
('citizen1', 'cit123', 'citizen');

INSERT INTO shelters (name, capacity, available_capacity, food, water, medicine, location) VALUES
('Government School', 500, 450, 100, 200, 50, 'Downtown Area'),
('Community Center', 300, 300, 75, 150, 30, 'Suburb Area'),
('Sports Complex', 800, 750, 200, 400, 100, 'Sports District');

INSERT INTO public_info (type, description, status) VALUES
('Alert', 'Flood warning in coastal area', 'Active'),
('Guideline', 'Keep emergency kit ready', 'Active'),
('Shelter', 'Govt School, Capacity: 500', 'Active'),
('Contact', 'Disaster Helpline: 108', 'Active');

INSERT INTO hazards (description, location, status, severity) VALUES
('Broken gas line reported', 'Main Street, Block A', 'Active', 'High'),
('Fallen tree blocking road', 'Oak Avenue', 'Active', 'Medium'),
('Power outage in sector 5', 'Industrial Area', 'Active', 'Critical');

