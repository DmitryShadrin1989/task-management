# Task tracking and management system
## Introduction
The Task Tracking Application is a comprehensive solution for managing tasks efficiently. It allows users to create, 
edit, and delete tasks, receive notifications about deadlines, categorize tasks, and collaborate within a team.
The application also offers detailed reports and statistics to help users keep track of their productivity.

## Features
 - **Task Management:** Create, edit, delete tasks.
 - **Notifications:** Receive reminders about task deadlines.
 - **Categorization and Tagging:** Organize tasks using categories and tags.
 - **Team Collaboration:** Assign tasks to team members and work together.
 - **Reports and Statistics:** Generate reports and view statistics on task completion.

## Architectural Patterns
 - **Microservices Architecture:** The application is built using a microservices architecture to ensure scalability and maintainability.
 - **REST API:** Services interact with each other through RESTful APIs.

## Application Architecture
The Task Tracking Application consists of the following services:
 - **Page Service:** Provides the user interface for interacting with the application.
 - **Task Service:** Manages tasks and their statuses.
 - **User Service:** Manages user information.
 - **Gateway Service:** Redirects to the target services and performs authentication and authorization.
 - **Batch Service:** Initializes data and can be used as an alternative interface using the Spring Shell
 - **Board Service:** Manages boards.
 - **Comment Service:** Manages comments.
 - **Config Server:** Manages the configurations of the application microservices
 - **Discovery Server:** Performs registration of services and provides information about them.

## Contact
For any inquiries or feedback, please contact dmitry.shadrin.alex1989@gmail.com.
 
 