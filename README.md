# Vinyl Library — Client/Server

A multi-client vinyl lending library system built with Java, using TCP sockets for communication and JSON for message formatting. Designed as a hands-on exploration of software design patterns in a networked environment.

## What It Does

The system lets multiple users browse, borrow, reserve, and return vinyl records from a shared library — all in real time. One central server manages the vinyl collection, and any number of JavaFX clients can connect, perform actions, and see updates as they happen.

When one client borrows a vinyl, every other connected client is immediately notified through a server broadcast.

## Architecture

**Server** — holds the vinyl library, accepts TCP socket connections, spawns a thread per client, processes JSON requests, and broadcasts state changes to all connected clients.

**Client** — JavaFX GUI using the MVVM pattern. Communicates with the server exclusively through JSON messages over a TCP socket. The GUI updates automatically when broadcasts arrive.

## Design Patterns

**State Pattern** — each vinyl has a state object (Available, Borrowed, Reserved, BorrowedAndReserved) that governs which actions are allowed. Transitions are handled by the state classes themselves.

**Strategy Pattern** — the server uses a `RequestHandler` interface with separate implementations for each request type (borrow, reserve, return, remove, get list). A factory selects the correct handler based on the incoming JSON message type.

**Observer Pattern** — `PropertyChangeSupport` notifies the ViewModel when messages arrive from the server. JavaFX property bindings keep the GUI in sync.

**Singleton Pattern** — `ServerLogger` provides a single logging instance that records all communication (messages, IP addresses, timestamps) to both the console and daily log files.

**MVVM Pattern** — the client separates View (FXML + Controller), ViewModel (data binding and server communication), and Model (SocketClient).

## Communication Protocol

All messages are single-line JSON objects sent over TCP.

### Client → Server

| Type | Example |
|------|---------|
| Get list | `{"type":"getList"}` |
| Borrow | `{"type":"borrow","title":"Abbey Road","userId":"user1"}` |
| Reserve | `{"type":"reserve","title":"Abbey Road","userId":"user1"}` |
| Return | `{"type":"return","title":"Abbey Road"}` |
| Remove | `{"type":"remove","title":"Abbey Road"}` |

### Server → Client

| Type | Example |
|------|---------|
| List response | `{"type":"list","vinyls":[...]}` |
| OK response | `{"type":"response","status":"ok","message":"..."}` |
| Error response | `{"type":"response","status":"error","message":"..."}` |
| State broadcast | `{"type":"broadcast","event":"stateChanged","title":"..."}` |
| Removal broadcast | `{"type":"broadcast","event":"removed","title":"..."}` |

## Tech Stack

- Java 21+
- JavaFX 21
- Gson (JSON)
- Maven
- TCP Sockets

## How to Run

1. Start the server: run `VinylServer.main()`
2. Start one or more clients: run `Main.main()`

Multiple clients can connect simultaneously. Each client operates independently and receives real-time updates.
