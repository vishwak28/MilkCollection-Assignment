# Milk Collection Backend

This is a backend for a dairy that collects milk from farmers twice a day and needs to know
which tanker goes where, and whether the milk reached the chilling plant on time.

It has two parts:
- Setting up villages, collection points, farmers, tankers and routes (the planning side)
- Running a collection for a day/shift and tracking it (the twice-a-day side)

## Tech used

- Java 17
- Spring Boot
- MySQL
- Maven

## How to run it

1. Make sure you have Java 17 and Maven installed.
2. Make sure MySQL is running, and create a database:
   ```sql
   CREATE DATABASE milk_collection;
   ```
3. By default the app connects with username `root` and password `password`. If your MySQL
   setup is different, set these environment variables before running:
   ```bash
   export DB_USER=your_username
   export DB_PASSWORD=your_password
   ```
4. From the project folder, run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
5. The app runs on `http://localhost:8080`.

When it starts for the first time, it automatically adds some sample data (a village, a couple
of collection points, farmers, a tanker, and a route) so there's something to test with right
away. It won't add this data again if you restart the app.

## Some endpoints to try

```
GET  /api/villages
POST /api/routes/{id}/stops
POST /api/runs                                  -> generate a run for a route/date
POST /api/runs/{id}/stops/{pointId}/arrive       -> mark a stop as done
POST /api/runs/{id}/complete                     -> finish the run
GET  /api/track/collection-points/{id}           -> check where the tanker is
```

## Assumptions I made

- There is only one chilling plant, so I didn't make a separate table for it.
- "Twice a day" means there are two routes for the same area, one for morning and one for
  evening, instead of one route that runs twice.
- To check if milk spoiled, I just compare the time between when the tanker starts a run and
  when it finishes the run, against a limit set on the route. I didn't do anything with GPS or
  per-stop timing since there's no location tracking involved.
- Milk quantity is recorded per collection point, not per farmer, even if a point has more than
  one farmer.
- The order in which the tanker visits stops is entered by a person, not calculated
  automatically.

## What I left out, and why

- **Login/authentication** – didn't add this since it wasn't the main problem being solved, and
  I wanted to spend the time on the routing/tracking logic instead.
- **Automatic route planning** – the app lets you assign stops to a route in order, but it
  doesn't figure out the best order on its own. That's a bigger problem on its own (route
  optimization) and felt out of scope for the time I had.
- **Live GPS tracking** – there's no real-time location. The app tracks progress based on the
  driver (or someone) marking each stop as done, which is the only way I could track location
  without extra hardware/data.
- **Editing or deleting** villages/farmers/tankers/routes after creating them – you can create
  and view them, but not update or delete. Didn't get to this in the time available.
- **Splitting milk quantity between farmers** at the same collection point – it's recorded as
  one total per stop instead of per farmer, since payments per farmer weren't part of the
  problem being asked.
