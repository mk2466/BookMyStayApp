# Book My Stay App

## Overview
This project demonstrates adding optional services to existing reservations without affecting room allocation or inventory.

Use Case 7 allows guests to select add-on services and calculates additional costs.

## Concepts Used
- Map<String, List<Service>> for reservation-to-services mapping
- Composition over inheritance
- Separation of core booking and optional services
- Cost aggregation

## How to Compile and Run
javac UseCase7AddOnServiceSelection.java
java UseCase7AddOnServiceSelection

## Output
- Shows services added to each reservation
- Displays total additional cost
- Keeps core booking and inventory unchanged
