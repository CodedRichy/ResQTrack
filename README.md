# ResQTrack
A Java-based Disaster Management System for reporting incidents, managing shelters, sending alerts, and coordinating rescue operations.

## Features
- **User Management**: Admin, volunteer, and citizen roles
- **Shelter Management**: Track shelter capacity, resources (food, water, medicine)
- **Task Management**: Assign and track rescue tasks
- **SOS Alerts**: Emergency alert system with location tracking
- **Incident Reports**: Report and track disaster incidents
- **Hazard Tracking**: Monitor and manage hazards
- **Public Information**: Share alerts, guidelines, and updates
- **Database Integration**: MySQL database for persistent data storage

## Prerequisites
- Java 8 or higher
- MySQL Server 5.7 or higher
- MySQL Connector/J (included in project)

## Database Setup

### 1. Install MySQL Server
Download and install MySQL Server from [mysql.com](https://dev.mysql.com/downloads/mysql/)

### 2. Create Database User
Connect to MySQL as root and create a user for the application:
```sql
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'mifa@123';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Database Configuration
The application is configured to connect to:
- **Host**: localhost
- **Port**: 3306
- **Database**: disaster_management_system
- **Username**: admin
- **Password**: mifa@123

To change these settings, edit `database/DatabaseConnector.java`.

### 4. Initialize Database
The database will be automatically initialized when you first run the application. The schema and sample data will be created automatically.

Alternatively, you can manually initialize the database by running:
```bash
java -cp ".;mysql-connector-java-8.0.33.jar" database.DatabaseInitializer
```

## Building and Running

### Option 1: Using Batch Files (Windows)
1. Download MySQL Connector/J and place `mysql-connector-java-8.0.33.jar` in the project root
2. Run `build.bat` to compile the project
3. Run `run.bat` to start the application

### Option 2: Manual Compilation
1. Download MySQL Connector/J and place `mysql-connector-java-8.0.33.jar` in the project root
2. Compile the project:
```bash
javac -cp ".;mysql-connector-java-8.0.33.jar" -d . database/*.java
javac -cp ".;mysql-connector-java-8.0.33.jar" -d . service/*.java
javac -cp ".;mysql-connector-java-8.0.33.jar" -d . userinterface/*.java
javac -cp ".;mysql-connector-java-8.0.33.jar" -d . main/*.java
```
3. Run the application:
```bash
java -cp ".;mysql-connector-java-8.0.33.jar" main.Main
```

## Project Structure
```
ResQTrack/
├── database/           # Database layer
│   ├── DatabaseConnector.java    # Database connection
│   ├── DatabaseSchema.sql        # Database schema
│   ├── DatabaseInitializer.java  # Database initialization
│   ├── BaseDAO.java             # Base DAO class
│   ├── *DAO.java                # Data Access Objects
│   └── DatabaseService.java     # Database service layer
├── service/            # Business logic layer
│   ├── User.java
│   ├── Shelter.java
│   ├── Task.java
│   ├── SosAlert.java
│   ├── Report.java
│   ├── Hazard.java
│   └── PublicInfo.java
├── userinterface/      # UI layer
│   ├── UserPanel.java
│   ├── ShelterPanel.java
│   ├── TaskPanel.java
│   ├── SosAlertPanel.java
│   ├── ReportPanel.java
│   ├── HazardPanel.java
│   └── PublicInfoPanel.java
├── main/               # Application entry point
│   └── Main.java
├── build.bat          # Windows build script
├── run.bat            # Windows run script
└── README.md
```

## Usage

### Login
The application starts with a login screen. Use these default credentials:
- **Admin**: username: `admin`, password: `admin123`
- **Volunteer**: username: `volunteer1`, password: `vol123`
- **Citizen**: username: `citizen1`, password: `cit123`

### Navigation
After login, you can access different modules through the tabbed interface:
- **Shelters**: Manage emergency shelters and resources
- **Tasks**: Assign and track rescue tasks
- **SOS Alerts**: View and manage emergency alerts
- **Reports**: Create and review incident reports
- **Hazards**: Track and manage hazards
- **Public Info**: Share public information and alerts

## Database Schema

The application uses the following main tables:
- `users` - User accounts and roles
- `shelters` - Emergency shelter information
- `tasks` - Rescue and relief tasks
- `sos_alerts` - Emergency SOS alerts
- `reports` - Incident reports
- `hazards` - Hazard tracking
- `public_info` - Public information and alerts

## Troubleshooting

### Database Connection Issues
1. Ensure MySQL server is running
2. Check database credentials in `DatabaseConnector.java`
3. Verify the database user has proper permissions
4. Check firewall settings if connecting to remote MySQL

### Compilation Issues
1. Ensure Java 8+ is installed
2. Verify MySQL Connector/J is in the project root
3. Check file paths and package declarations

### Runtime Issues
1. Check console output for error messages
2. Verify database connection and permissions
3. Ensure all required tables exist

## Contributing
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License
This project is open source and available under the MIT License.