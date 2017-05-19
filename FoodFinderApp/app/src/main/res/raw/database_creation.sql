CREATE TABLE userInfo(
    _id	INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    surname TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    gender INTEGER DEFAULT 0,
    description TEXT NOT NULL
);

CREATE TABLE favorite_restaurants(
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    userID INT FOREIGN KEY,
    restaurantName TEXT NOT NULL
);

CREATE TABLE recent_places(
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    restaurantName TEXT NOT NULL,
    userId INT FOREIGN KEY
);