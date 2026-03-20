# Book My Stay App

## Overview
This project demonstrates handling of booking requests in a hotel system using a queue.

Use Case 5 collects guest booking requests in a first-come-first-served (FIFO) manner without modifying inventory.

## Concepts Used
- Queue (FIFO)
- Request ordering
- Decoupling request intake from allocation

## How to Compile and Run
javac UseCase5BookingRequestQueue.java
java UseCase5BookingRequestQueue

## Output
- Shows booking requests being added to the queue
- Preserves arrival order
- Prepares requests for later allocation
