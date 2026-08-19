#  HomeAway From Home (Homeaway)

## Authors
This project was developed by **Giovanna Cardoso** and **Elsa Coimbra**

## Project Overview
This project is a location-based service application designed to help international students find and manage key services in campus-oriented university towns. Operating within a user-defined geographic bounding rectangle (defined by latitude and longitude coordinates), the application ensures all activities and services remain within the specified town boundaries.

### Core Features & Entities
* **Geographic Bounding Box & Persistence:** Defines system boundaries and supports state persistence by saving and loading active geographic areas to and from files.
* **Service Management:** Handles three distinct service types:
  * **Eating:** Low-budget restaurants and canteens, managing daily menu prices and seating capacities.
  * **Lodging:** Student residences and rooms, tracking monthly rent prices and room capacities.
  * **Leisure:** Cultural and entertainment venues, managing ticket prices and student discount percentages.
* **Student Behavior Simulations:** Models real-time student interactions based on three distinct lifestyle profiles:
  * **Bookish:** Focuses on studying and cultural sites, maintaining a history of visited leisure locations.
  * **Outgoing:** Explores the city without restrictions, storing a complete history of all visited locations.
  * **Thrifty:** Focuses strictly on saving money, tracking minimal eating and lodging costs, and only moving residences when finding a strictly cheaper alternative.
* **Distance Calculations:** Calculates proximity between students and services using Manhattan Distance:

$$d(l^1, l^2) = |l^1_{\text{lat}} - l^2_{\text{lat}}| + |l^1_{\text{long}} - l^2_{\text{long}}|$$

* **Star Ratings & Ranking:** Integrates a 1-to-5 star evaluation system, computing rounded average scores and ordering services by overall quality or proximity.
* **Review Tag Filtering:** Filters services based on specific keywords contained within user reviews.

---

## 🏗️ System Architecture & App Division
To maintain high cohesion and separation of concerns, the project's main functionalities are structured and divided into dedicated sub-applications (modules):

* **Main App (`Main` and  `AreaClass`):** Acts as the entry point and CLI controller, interpreting user commands, managing geographic bounds, and coordinating save/load operations.
* **Services App (`ServiceAreaClass`):** Dedicated to service management, including adding new services, managing capacity, updating star ratings, ranking services, and processing review tags.
* **Students App (`StudentAreaClass`):** Handles student management, tracking student registrations, country-based filtering, location movements (`go`, `move`), and visit histories.

---

## 📦 Custom Data Structures (`dataStructures`)
In compliance with project requirements, no standard Java collection classes (`java.util`) were used for data storage or manipulation. All data management relies on custom-built data structures implemented from scratch, including:

* **Trees:** Binary Search Trees (BST) and self-balancing AVL Trees for logarithmic $O(\log n)$ ordered lookups.
* **Hash Tables:** Custom Hash Table implementations utilizing both closed addressing (open addressing) and separate chaining (using singly linked list buckets).
* **Lists:**
  * Singly Linked List & Doubly Linked List
  * List in Array (array-based dynamic list implementations)
* **Iterators:** Specialized custom Iterator implementations designed for traversing internal list and tree structures.

---

## 🖼️ Class Diagram
A complete UML Class Diagram detailing the system architecture, class relationships, inheritance hierarchies, and package structures is included in the project repository files (`` / project documentation).
