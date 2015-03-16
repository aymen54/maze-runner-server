# Maze runner server

## Task

You wake up in a dark place without remembering what happened to you. You start to search around for an exit, and quickly realize that you are in a maze.
The *you* in this task is the maze runner client, which tries to find the exit. The only way to know where it can go next is the maze runner service.
The service returns information about moves within the maze for adjacent squares. With this information the client is able to find its way out of the maze.

Write a program, that will interact as a client with the provided server to find a path from starting position to the exit of the maze.
Your program should take two command line parameters `code` (maze code) and `url` (URL to maze service) and print the path.

Remember to create a "production ready" application. Clients should make use of:
- logging,
- correct exception handling,
- unit tests.

### Notes
- It is guaranteed that the maze contains one starting position and one exit, respectively.
- The maze consists of the following characters: '.' - field, '#' - wall, '@' - starting position, 'x' - exit.
- You should implement a REST API client, which will query the provided service and print a path, which leads from starting position to the exit.
- The REST API Swagger documentation is available at `/swagger-ui`, i.e. `http://localhost:8080/swagger-ui`.

## Service

The maze runner service communicates with the clients over a REST interface.
The server is stateless, its only concern is giving information about the maze. The server supports the following endpoints:

The responses for available endpoints are assuming, that the server hosts `maze-1` of size 3x3.
The starting position located at `(1, 0)`, the exit at `(2, 2)`.

```
#@#
#.#
..x
```

### `GET /mazes`

Lists mazes with their dimensions.

**Response body:**
```
[
    {
        "code": "maze-1",
        "width": 3,
        "height": 3
    }
]
```

### `GET /mazes/{code}/position/start`
Returns the coordinate of the start position. Coordinates start at the upper left corner with indices starting at 0.
The bottom-right corner has the coordinates (width - 1, height - 1).

**Response:**
- `200 OK`
- `404 Not found`

**Response body:**
```
{
    "x": 1,
    "y": 0
}

```
### `POST /mazes/{code}/position`

When a client tries to move it has to give its current coordinates, and the direction, where it wants to go to.
The service checks whether the given coordinate is valid one. The only valid originator coordinate is a way
or starting position. This check is important otherwise the clients could map the maze by brute force.

In case the originator coordinate is invalid the maze service returns `418 I'm a teapot`.
The directions can be: `NORTH`, `WEST`, `SOUTH`, `EAST`.

**Request body:**
```
{
    "from": {
       "x": 1,
       "y": 0
    },
    "direction": "SOUTH"
}
```

**Response:**
- `200 OK`
- `404 Not found`, if the maze is not found
- `418 I'm a teapot` - in case the move is not valid (RFC 2324)

** Response body (in case of `200 OK`):**
```
{
    "position": {
        "x": 1,
        "y": 1
    },
    "field": "."
}
```

## Running the server

Clone the project and start the Spring Boot application: `mvn clean spring-boot:run`.
The server is available at `http://localhost:8080`. A Swagger documentation of the REST API is hosted at `http://localhost:8080/swagger-ui`.

You can also run the application via Docker:

```
mvn clean package
docker build -t maze-runner:1.0 .
docker run -it -p 8080:8080 maze-runner:1.0
```

After running the application you can retrieve the available mazes at: `http://localhost:8080/mazes`.
